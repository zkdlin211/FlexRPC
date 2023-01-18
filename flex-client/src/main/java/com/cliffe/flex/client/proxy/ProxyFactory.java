package com.cliffe.flex.client.proxy;

import com.cliffe.flex.client.annotation.FlexRpc;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 5:44 p.m.
 */
public interface ProxyFactory {
	<T> T newProxyInstance(Class<T> clazz, FlexRpc flexRpc);
}
