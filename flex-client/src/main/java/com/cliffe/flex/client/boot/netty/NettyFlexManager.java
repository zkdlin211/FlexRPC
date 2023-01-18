package com.cliffe.flex.client.boot.netty;

import com.cliffe.flex.client.annotation.NIO;
import com.cliffe.flex.client.constant.Strategy;
import com.cliffe.flex.client.handler.RpcResponseMessageHandler;
import com.cliffe.flex.client.nio.NIOHandler;
import com.cliffe.flex.core.exception.FlexException;
import com.cliffe.flex.core.spring.BeanFactoryUtil;
import io.netty.channel.Channel;
import io.netty.util.concurrent.DefaultPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

/**
 * @Author: Cliffe
 * @Date: 2023-01-15 2:49 p.m.
 */
@Component
public class NettyFlexManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(NettyFlexManager.class);

	@Autowired
	private BeanFactoryUtil beanFactoryUtil;

	@Autowired
	@Qualifier("businessNioExecutorService")
	private ExecutorService executorService;


	public Object nioHandle(int sequenceId, Method method, Object[] parameters, NIO nio, Channel channel) {
		Class<? extends NIOHandler> nioHandler = nio.nioHandler();
		if (nioHandler == null) {
			throw new FlexException("@NIO must registers with a NioHandler");
		}
		executorService.submit(() -> {
			Object result = task(sequenceId, nio, method, parameters, channel);
			try {
				NIOHandler handler = nioHandler.newInstance();
				handler.handleResponse(result);
			} catch (InstantiationException | IllegalAccessException e) {
				errorHandle(nio, e, method, parameters);
			}
		});
		return null;
	}

	public Object task(int sequenceId, NIO nio, Method method, Object[] parameters, Channel channel) {
		DefaultPromise<Object> promise = new DefaultPromise<>(channel.eventLoop());
		RpcResponseMessageHandler.PROMISE_MAP.put(sequenceId, promise);
		try {
			promise.await();
		} catch (InterruptedException e) {
			errorHandle(nio, e, method, parameters);
		}
		if (promise.isSuccess()) {
			return promise.getNow();
		}
		errorHandle(nio, new FlexException(promise.cause()), method, parameters);
		return null;
	}

	private void errorHandle(NIO nio, Exception e, Method method, Object[] parameters) {
		Strategy fallbackStrategy = nio.fallbackStrategy();
		switch (fallbackStrategy){
			case MQ:

				break;
			case METHOD:
				Class<?> clazz = nio.fallbackClass();
				String methodName = nio.fallbackMethod();
				try {
					Method fallback = clazz.getDeclaredMethod(methodName, method.getParameterTypes());
					fallback.setAccessible(true);
					fallback.invoke(getInstance(clazz), parameters);
				} catch (NoSuchMethodException | InstantiationException |
						 InvocationTargetException | IllegalAccessException ex) {
					LOGGER.error("invoke fallback method error: {}", ex.getMessage());
					ex.printStackTrace();
				}
				break;
			case NULL:
				throw new FlexException(e);
			default:
				throw new IllegalStateException("Unexpected value: " + fallbackStrategy);
		}
	}
	private Object getInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException {
		Object bean = beanFactoryUtil.getBean(clazz);
		if(bean == null){
			bean = clazz.newInstance();
		}
		return bean;
	}
}
