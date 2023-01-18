package com.cliffe.flex.client.cluster.lb;

import com.cliffe.flex.core.provider.ServiceProvider;

import java.util.List;

/**
 * @Author: Cliffe
 * @Date: 2023-01-15 3:08 p.m.
 */
public interface LoadBalanceSelector {

	ServiceProvider select(List<ServiceProvider> serviceProviderList, String interfaceName);

	String strategy();
}
