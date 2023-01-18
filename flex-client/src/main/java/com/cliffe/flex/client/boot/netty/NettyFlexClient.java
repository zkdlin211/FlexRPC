package com.cliffe.flex.client.boot.netty;

import com.cliffe.flex.client.FlexClientConfigurationProperties;
import com.cliffe.flex.client.FlexClientExecutorConfigurationProperties;
import com.cliffe.flex.client.FlexZkClientConfigurationProperties;
import com.cliffe.flex.client.annotation.FlexRpc;
import com.cliffe.flex.client.annotation.NIO;
import com.cliffe.flex.client.boot.FlexClient;
import com.cliffe.flex.client.cluster.lb.LoadBalanceSelector;
import com.cliffe.flex.client.cluster.lb.LoadBalanceSelectorRegistry;
import com.cliffe.flex.core.exception.FlexException;
import com.cliffe.flex.core.message.Message;
import com.cliffe.flex.core.message.RpcRequestMessage;
import com.cliffe.flex.core.provider.ServiceProvider;
import com.cliffe.flex.core.provider.ServiceProviderCache;
import com.cliffe.flex.core.rpc.netty.codec.RpcMessageCodec;
import com.cliffe.flex.client.handler.RpcResponseMessageHandler;
import com.cliffe.flex.core.rpc.netty.protocol.FlexProtocolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @Author: Cliffe
 * @Date: 2023-01-12 11:25 a.m.
 */
@Component
//@ConditionalOnMissingBean(FlexClient.class)
public class NettyFlexClient implements FlexClient {

	@Autowired
	private FlexClientConfigurationProperties cfgProperties;

	private static final Logger LOGGER = LoggerFactory.getLogger(NettyFlexClient.class);

	private static final Map<String, Channel> channelMap = new HashMap<>();

	@Autowired
	@Qualifier(value = "flexClientExecutorService")
	private ExecutorService flexClientExecutorService;

	@Autowired
	private FlexClientExecutorConfigurationProperties executorConfigurationProperties;

	@Autowired
	private RpcResponseMessageHandler rpcResponseMessageHandler;

	@Autowired
	private ServiceProviderCache serviceProviderCache;

	@Autowired
	private NettyFlexManager nettyFlexManager;

	@Autowired
	private LoadBalanceSelectorRegistry loadBalanceSelectorRegistry;

	@Autowired
	@Qualifier("businessNioExecutorService")
	private ExecutorService executorService;


	@Override
	public Object send(Message msg, Method method, FlexRpc flexRpc) {
		if (!(msg instanceof RpcRequestMessage)) {
			throw new FlexException("wrong type of message!");
		}
		RpcRequestMessage message = (RpcRequestMessage) msg;
		List<ServiceProvider> serviceProviders = serviceProviderCache.get(message.getInterfaceName());
		if (serviceProviders.isEmpty()) {
			throw new FlexException("no available providers" + message.getInterfaceName() + "found on registry centre!");
		}
		//
		LoadBalanceSelector balanceSelector = loadBalanceSelectorRegistry.get(cfgProperties.getLbStrategy());
		ServiceProvider serviceProvider = balanceSelector.select(serviceProviders, message.getInterfaceName());
		Channel channel = getChannel(serviceProvider);
		channel.writeAndFlush(message);
		NIO nio = method.getAnnotation(NIO.class);
		if (nio != null) {
			return nettyFlexManager.nioHandle(message.getSequenceId(), method, message.getParameterValue(), nio, channel);
		}
		return nettyFlexManager.task(message.getSequenceId(), nio, method, message.getParameterValue(), channel);
	}

	private void initChannel(ServiceProvider serviceProvider) {
		NioEventLoopGroup worker = new NioEventLoopGroup(executorConfigurationProperties.getCorePoolSize(), flexClientExecutorService);
		LoggingHandler LOGGING_HANDLER = new LoggingHandler(cfgProperties.getLoggingLevel());
		Bootstrap bootstrap = new Bootstrap()
				.group(worker)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.handler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel channel) {
						channel.pipeline()
								.addLast("loggingHandler", LOGGING_HANDLER)
								.addLast("frameDecoder", new FlexProtocolFrameDecoder())
								.addLast("messageCodec", new RpcMessageCodec())
								.addLast("responseHandler", rpcResponseMessageHandler);
					}
				});
		try {
			Channel channel = bootstrap.connect(new InetSocketAddress(serviceProvider.getServerIp(), serviceProvider.getRpcPort())).sync().channel();
			channelMap.put(serviceProvider.getIpWithPort(), channel);
			LOGGER.info("FLEX: netty client started successfully and listening on {}. ", cfgProperties.getRpcServerPort());
			channel.closeFuture().addListener(promise -> {
				worker.shutdownGracefully();
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Channel getChannel(ServiceProvider serviceProvider) {
		String key = serviceProvider.getIpWithPort();
		if (channelMap.get(key) == null) {
			synchronized (NettyFlexClient.class) {
				if (channelMap.get(key) == null) {
					executorService.submit(() -> {
						initChannel(serviceProvider);
					});
				}
			}
		}
		return channelMap.get(key);
	}
}
