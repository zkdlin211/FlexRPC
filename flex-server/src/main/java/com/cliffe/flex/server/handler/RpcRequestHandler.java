package com.cliffe.flex.server.handler;

import com.cliffe.flex.core.message.RpcRequestMessage;
import com.cliffe.flex.core.message.RpcResponseMessage;
import com.cliffe.flex.core.serialize.SerializationRegistrar;
import com.cliffe.flex.core.spring.BeanFactoryUtil;
import com.cliffe.flex.server.FlexServerConfigurationProperties;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 11:11 上午
 */
@Component
@ChannelHandler.Sharable
public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RpcRequestHandler.class);

	@Autowired
	private FlexServerConfigurationProperties cfgProperties;

	@Autowired
	private BeanFactoryUtil beanFactoryUtil;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage request) {
		LOGGER.info("receive request from {}", request);
		RpcResponseMessage responseMessage = new RpcResponseMessage();
		responseMessage.setSequenceId(request.getSequenceId());
		try {
			Class<?> interfaceClass = Class.forName(request.getInterfaceName());
			String methodName = request.getMethodName();
			Class<?>[] parameterTypes = request.getParameterTypes();
			Object[] parameterValue = request.getParameterValue();
			Object bean = beanFactoryUtil.getBean(interfaceClass);
			if(bean == null){
				throw new NullPointerException("BeanFactory does not contain any implements of " + interfaceClass+". ");
			}
			Method method = bean.getClass().getMethod(methodName, parameterTypes);
			Object result = method.invoke(bean, parameterValue);
			responseMessage.setSerializationType(SerializationRegistrar.getSerializationNo(cfgProperties.getSerializationType()));
			responseMessage.setReturnValue(result);
		} catch (NullPointerException | NoSuchMethodException | SecurityException | IllegalAccessException |
				 IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
			e.printStackTrace();
			responseMessage.setExceptionValue(e);
			LOGGER.error("FLEX: rpc server invoke error with message: {}", e.getMessage());
		} finally {
			LOGGER.info("FLEX: finish execution with no exception, result: {}", responseMessage.getReturnValue());
			ctx.writeAndFlush(responseMessage);
		}
	}
}
