package com.cliffe.flex.client;

import com.cliffe.flex.client.cache.CachedExecutorService;
import com.cliffe.flex.client.cache.FIFOCachedExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Cliffe
 * @Date: 2023-01-12 4:15 p.m.
 */
@Configuration
public class FlexClientBusinessNioExecutorConfiguration {

	@Autowired
	private FlexClientBusinessNioExecutorConfigurationProperties executorCfg;

	@Bean(name = "businessNioExecutorService")
	@ConditionalOnMissingBean(name = "businessNioExecutorService")
	@ConditionalOnProperty(prefix = "flex.client.nio.thread", value = "enableCache", havingValue = "false", matchIfMissing = true)
	public ExecutorService executorService(){
		return new ThreadPoolExecutor(
				executorCfg.getCorePoolSize(),
				executorCfg.getMaximumPoolSize(),
				executorCfg.getKeepAliveMillisecondTime(),
				TimeUnit.MILLISECONDS,
				new SynchronousQueue<>()
		);
	}

//	@Bean(name = "businessNioExecutorService")
//	@ConditionalOnMissingBean(name = "businessNioExecutorService")
//	@ConditionalOnProperty(prefix = "flex.client.nio.thread", value = "enableCache", havingValue = "true")
//	public CachedExecutorService cachedExecutorService(){
//		return new FIFOCachedExecutorService()
//	}

}
