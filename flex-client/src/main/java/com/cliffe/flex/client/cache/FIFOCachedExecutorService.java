package com.cliffe.flex.client.cache;

import com.cliffe.flex.client.FlexClientExecutorConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

@Component
public class FIFOCachedExecutorService extends CachedExecutorService {
	private final LinkedList<String> order;

	protected final int maxCacheSize;


	@Autowired
	public FIFOCachedExecutorService(@Qualifier("flexClientExecutorService") Executor delegate,
									 FlexClientExecutorConfigurationProperties executorCfg) {
		super(executorCfg.getCorePoolSize(),
				executorCfg.getMaximumPoolSize(),
				executorCfg.getKeepAliveMillisecondTime(),
				TimeUnit.MILLISECONDS,
				new SynchronousQueue<>()
		);
		this.maxCacheSize = executorCfg.getMaxCacheSize();
		this.order = new LinkedList<>();
	}

	@Override
	protected void tryPut(String cacheKey, Object result) {
		Map<String, Object> cache = getCache();
		if (cache.size() >= maxCacheSize) {
			String oldest = order.remove();
			cache.remove(oldest);
		}
		order.add(cacheKey);
		cache.put(cacheKey, result);
	}
}
