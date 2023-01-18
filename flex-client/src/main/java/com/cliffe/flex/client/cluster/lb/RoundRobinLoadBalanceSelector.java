package com.cliffe.flex.client.cluster.lb;

import com.cliffe.flex.core.provider.ServiceProvider;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: Cliffe
 * @Date: 2023-01-15 3:35 p.m.
 */
public class RoundRobinLoadBalanceSelector implements LoadBalanceSelector{

	private static final Map<String, Integer> indexMap = new HashMap<>();

	private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	private final ReentrantReadWriteLock.ReadLock rLock = rwLock.readLock();
	private final ReentrantReadWriteLock.WriteLock wLock = rwLock.writeLock();

	@Override
	public ServiceProvider select(List<ServiceProvider> serviceProviderList, String interfaceName) {
		serviceProviderList.sort(Comparator.comparingInt(ServiceProvider::getNetworkPort));
		int size = serviceProviderList.size();
		try {
			rLock.lock();
			if(!indexMap.containsKey(interfaceName)){
				update(interfaceName,1 % size);
				return serviceProviderList.get(0);
			}
			Integer index = indexMap.get(interfaceName);
			update(interfaceName, (index+1)%size);
			return serviceProviderList.get(index);
		}finally {
			rLock.unlock();
		}
	}

	private void update(String interfaceName, int index) {
		try {
			wLock.lock();
			indexMap.put(interfaceName, index);
		} finally {
			wLock.unlock();
		}

	}

	@Override
	public String strategy() {
		return "roundRobin";
	}
}
