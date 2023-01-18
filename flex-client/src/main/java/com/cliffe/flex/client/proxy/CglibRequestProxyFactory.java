package com.cliffe.flex.client.proxy;

import com.cliffe.flex.client.FlexClientConfigurationProperties;
import com.cliffe.flex.client.annotation.FlexRpc;
import com.cliffe.flex.client.boot.FlexClient;
import com.cliffe.flex.client.cache.ProxyFactoryCache;
import com.cliffe.flex.client.util.SequenceIdGenerator;
import com.cliffe.flex.core.message.RpcRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 5:46 p.m.
 */
@Component
//@ConditionalOnMissingBean(ProxyFactory.class)
@ConditionalOnProperty(prefix = "flex.client", name = "proxyType", havingValue = "cglib", matchIfMissing = true)
public class CglibRequestProxyFactory implements ProxyFactory {

	@Resource
	private ProxyFactoryCache proxyFactoryCache;

	@Autowired
	private FlexClient flexClient;


	@Autowired
	private FlexClientConfigurationProperties flexClientConfigurationProperties;

	@Autowired

	private static final Logger LOGGER = LoggerFactory.getLogger(CglibRequestProxyFactory.class);

	@Override
	public <T> T newProxyInstance(Class<T> clazz, FlexRpc flexRpc) {
		Object proxy = proxyFactoryCache.get(clazz);
		if (proxy != null)
			return (T) proxy;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		if(flexRpc.enableCache()){
			enhancer.setCallback(new CachedCglibProxyCallBackHandler(flexRpc));
		}else{
			enhancer.setCallback(new CglibProxyCallBackHandler(flexRpc));
		}
		proxy = enhancer.create();
		proxyFactoryCache.put(clazz, proxy);
		return (T) proxy;
	}

	class CachedCglibProxyCallBackHandler implements MethodInterceptor{

		private FlexRpc flexRpc;

		public CachedCglibProxyCallBackHandler(FlexRpc flexRpc) {
			this.flexRpc = flexRpc;
		}

		@Override
		public Object intercept(Object proxy, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
			return null;
		}
	}

	class CglibProxyCallBackHandler implements MethodInterceptor {

		private FlexRpc flexRpc;

		public CglibProxyCallBackHandler(FlexRpc flexRpc) {
			this.flexRpc = flexRpc;
		}

		public Object intercept(Object proxy, Method method, Object[] parameters, MethodProxy methodProxy) throws Throwable {
			if (ReflectionUtils.isObjectMethod(method)) {
				return method.invoke(method.getDeclaringClass().newInstance(), parameters);
			}
			int sequenceId = SequenceIdGenerator.nextId();
			String interfaceName = flexRpc.interfaceName();
			if(StringUtils.isEmpty(interfaceName)){
				interfaceName = method.getDeclaringClass().getName();
			}
			RpcRequestMessage message = new RpcRequestMessage.Builder()
					.withSerializationType(flexClientConfigurationProperties.getSerializationType())
					.withSequenceId(sequenceId)
					.withInterfaceName(interfaceName)
					.withMethodName(method.getName())
					.withParameterTypes(method.getParameterTypes())
					.withParameterValue(parameters)
					.build();
			return flexClient.send(message, method, flexRpc);
		}
	}
}
