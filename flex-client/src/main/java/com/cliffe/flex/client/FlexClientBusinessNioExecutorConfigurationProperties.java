package com.cliffe.flex.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Cliffe
 * @Date: 2023-01-12 9:38 a.m.
 */
@ConfigurationProperties(prefix = "flex.client.nio.thread")
public class FlexClientBusinessNioExecutorConfigurationProperties {

	private int corePoolSize = 0;

	private int maximumPoolSize = Integer.MAX_VALUE;

	private long keepAliveMillisecondTime = 3600;

	private int maxCacheSize = 10000;
	private int maxProxyFactoryCacheSize = 10000;

	private boolean enableCache = false;

	public boolean isEnableCache() {
		return enableCache;
	}

	public void setEnableCache(boolean enableCache) {
		this.enableCache = enableCache;
	}

	public int getMaxProxyFactoryCacheSize() {
		return maxProxyFactoryCacheSize;
	}

	public void setMaxProxyFactoryCacheSize(int maxProxyFactoryCacheSize) {
		this.maxProxyFactoryCacheSize = maxProxyFactoryCacheSize;
	}

	public int getMaxCacheSize() {
		return maxCacheSize;
	}

	public void setMaxCacheSize(int maxCacheSize) {
		this.maxCacheSize = maxCacheSize;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public long getKeepAliveMillisecondTime() {
		return keepAliveMillisecondTime;
	}

	public void setKeepAliveMillisecondTime(long keepAliveMillisecondTime) {
		this.keepAliveMillisecondTime = keepAliveMillisecondTime;
	}
}
