package com.cliffe.flex.client;

import com.cliffe.flex.client.cache.FIFOProxyFactoryCache;
import com.cliffe.flex.client.cache.ProxyFactoryCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;
import java.util.concurrent.*;

/**
 * @Author: Cliffe
 * @Date: 2023-01-12 9:38 a.m.
 */
@EnableConfigurationProperties({FlexClientExecutorConfigurationProperties.class})
public class FlexClientExecutorAutoConfiguration {

	@Autowired
	private FlexClientExecutorConfigurationProperties executorCfg;
	@Bean(name = "flexClientExecutorService")
	@ConditionalOnMissingBean(name = "flexClientExecutorService")
	public ExecutorService executorService(){
		 return new ThreadPoolExecutor(
				 executorCfg.getCorePoolSize(),
				 executorCfg.getMaximumPoolSize(),
				 executorCfg.getKeepAliveMillisecondTime(),
				 TimeUnit.MILLISECONDS,
				 new SynchronousQueue<>()
				 );
	}
}
