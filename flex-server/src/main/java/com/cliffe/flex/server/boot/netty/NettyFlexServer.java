package com.cliffe.flex.server.boot.netty;

import com.cliffe.flex.core.rpc.netty.codec.RpcMessageCodec;
import com.cliffe.flex.core.rpc.netty.protocol.FlexProtocolFrameDecoder;
import com.cliffe.flex.server.FlexServerConfigurationProperties;
import com.cliffe.flex.server.boot.FlexServer;
import com.cliffe.flex.server.handler.RpcRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.cliffe.flex.server.boot.netty.NettyFlexServerConfiguration.BOSS_EVENT_LOOP_GROUP_THREAD_COUNT;
import static com.cliffe.flex.server.boot.netty.NettyFlexServerConfiguration.WORKER_EVENT_LOOP_GROUP_THREAD_COUNT;

/**
 * @Author: Cliffe
 * @Date: 2023-01-09 8:27 上午
 */
@Component
public class NettyFlexServer implements FlexServer {

	@Autowired
	private FlexServerConfigurationProperties cfgProperties;

	private static final Logger LOGGER = LoggerFactory.getLogger(NettyFlexServer.class);

	@Autowired
	private RpcRequestHandler rpcRequestHandler;

	@Override
	public void start() {
		EventLoopGroup boss = new NioEventLoopGroup(
				BOSS_EVENT_LOOP_GROUP_THREAD_COUNT, new DefaultThreadFactory("boss"));
		EventLoopGroup worker = new NioEventLoopGroup(
				WORKER_EVENT_LOOP_GROUP_THREAD_COUNT, new DefaultThreadFactory("worker"));
		EventExecutorGroup business = new UnorderedThreadPoolEventExecutor(
				NettyRuntime.availableProcessors() * 2, new DefaultThreadFactory("business"));
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(boss, worker)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024)
					.childOption(ChannelOption.TCP_NODELAY, true)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.handler(new LoggingHandler(LogLevel.DEBUG))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel channel) throws Exception {
							channel.pipeline()
									.addLast("frameDecoder", new FlexProtocolFrameDecoder())
									.addLast("messageCodec",new RpcMessageCodec())
									.addLast("requestHandler", rpcRequestHandler);
						}
					});
			ChannelFuture future = serverBootstrap.bind(cfgProperties.getRpcServerPort()).sync();
			LOGGER.info("FLEX: netty server started on {} successfully. ", cfgProperties.getRpcServerPort());
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
			LOGGER.error("server error: " + e.getMessage());
		} finally {
			LOGGER.info("FLEX: server is ready to shutdown.");
			boss.shutdownGracefully();
			worker.shutdownGracefully();
			business.shutdownGracefully();
			LOGGER.info("FLEX: server finished shutdown.");
		}

	}

}
