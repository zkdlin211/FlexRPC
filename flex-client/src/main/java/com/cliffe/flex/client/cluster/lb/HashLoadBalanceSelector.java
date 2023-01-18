package com.cliffe.flex.client.cluster.lb;

import com.cliffe.flex.core.provider.ServiceProvider;
import com.cliffe.flex.core.util.IpUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Cliffe
 * @Date: 2023-01-15 3:15 p.m.
 */
@Component
public class HashLoadBalanceSelector implements LoadBalanceSelector{
	@Override
	public ServiceProvider select(List<ServiceProvider> serviceProviderList, String interfaceName) {
		int hashCode = IpUtil.getRealIp().hashCode();
		int index = Math.abs(hashCode % serviceProviderList.size());
		return serviceProviderList.get(index);
	}

	@Override
	public String strategy() {
		return "hash";
	}
}
