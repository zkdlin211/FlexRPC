package com.cliffe.flex.client;

import com.cliffe.flex.client.cache.FIFOProxyFactoryCache;
import com.cliffe.flex.client.cache.ProxyFactoryCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Cliffe
 * @Date: 2023-01-12 9:38 a.m.
 */
@Import(FlexClientBusinessNioExecutorConfiguration.class)
@EnableConfigurationProperties(FlexClientBusinessNioExecutorConfigurationProperties.class)
public class FlexClientBusinessExecutorAutoConfiguration {
}
