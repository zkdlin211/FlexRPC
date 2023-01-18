package com.cliffe.flex.client.annotation;

import com.cliffe.flex.client.proxy.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 5:27 p.m.
 */
@Component
public class FlexRpcAnnotationPostProcessor implements BeanPostProcessor {

	@Autowired
	private ProxyFactory proxyFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(FlexRpcAnnotationPostProcessor.class);

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		for (Field field : bean.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			FlexRpc flexRpc = field.getAnnotation(FlexRpc.class);
			if(flexRpc == null)
				continue;
			Object proxy = proxyFactory.newProxyInstance(field.getType(), flexRpc);
			LOGGER.debug("FLEX: @FlexRpc: generated a proxy for {} successfully.", field);
			if(proxy != null){
				try {
					field.set(bean, proxy);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return bean;
	}
}
