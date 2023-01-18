package com.cliffe.flex.client.cluster.lb;

import com.cliffe.flex.core.provider.ServiceProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * @Author: Cliffe
 * @Date: 2023-01-15 3:10 p.m.
 */
@Component
public class RandomLoadBalanceSelector implements LoadBalanceSelector{

	private static final Random random = new Random();
	@Override
	public ServiceProvider select(List<ServiceProvider> serviceProviderList, String interfaceName) {
		return serviceProviderList.get(random.nextInt(serviceProviderList.size()));
	}

	@Override
	public String strategy() {
		return "random";
	}
}
