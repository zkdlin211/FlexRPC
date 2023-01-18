package com.cliffe.flex.client.cache;

import com.cliffe.flex.client.FlexClientExecutorConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Cliffe
 * @Date: 2023-01-12 10:40 a.m.
 */
@Component
public abstract class ProxyFactoryCache {

	protected final Map<Class<?>, Object> cache;

	public ProxyFactoryCache() {
		this.cache = new ConcurrentHashMap<>();
	}
	public Object get(Class<?> key){
		return cache.getOrDefault(key, null);
	}

	public abstract void put(Class<?> key, Object value);
}
