package com.cliffe.flex.client.handler;

import com.cliffe.flex.core.message.RpcResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Cliffe
 * @Date: 2023-01-12 11:28 a.m.
 */
@ChannelHandler.Sharable
@Component
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {
	public static final Map<Integer, Promise<Object>> PROMISE_MAP = new ConcurrentHashMap<>();

	private static final Logger LOGGER = LoggerFactory.getLogger(RpcResponseMessageHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg){
		Promise<Object> promise = PROMISE_MAP.remove(msg.getSequenceId());
		if(promise == null) return;
		if(msg.getExceptionValue() != null){
			LOGGER.error("FLEX: rpc execution failed with exception:");
			msg.getExceptionValue().printStackTrace();
			promise.setFailure(msg.getExceptionValue());
		}else{
			LOGGER.info("FLEX: rpc execution success.");
			promise.setSuccess(msg.getReturnValue());
		}
	}
}
