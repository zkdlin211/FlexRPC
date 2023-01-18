package com.cliffe.flex.client.cache;

import com.cliffe.flex.client.FlexClientExecutorConfigurationProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 * @Author: Cliffe
 * @Date: 2023-01-12 10:44 a.m.
 */
@Component
public class FIFOProxyFactoryCache extends ProxyFactoryCache {

	private final LinkedList<String> order;

	private final int maxProxyFactoryCacheSize;


	@Autowired
	public FIFOProxyFactoryCache(FlexClientExecutorConfigurationProperties executorConfigurationProperties) {
		this.maxProxyFactoryCacheSize = executorConfigurationProperties.getMaxCacheSize();
		this.order = new LinkedList<>();
	}

	@Override
	public void put(Class<?> key, Object value) {
		if (cache.size() >= maxProxyFactoryCacheSize) {
			String oldest = order.remove();
			cache.remove(oldest);
		}
		cache.put(key, value);
	}
}
