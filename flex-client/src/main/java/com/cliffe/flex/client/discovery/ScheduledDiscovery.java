package com.cliffe.flex.client.discovery;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Cliffe
 * @Date: 2023-01-14 3:13 p.m.
 */
public abstract class ScheduledDiscovery {

	protected final ScheduledExecutorService executorService;

	public ScheduledDiscovery(ScheduledExecutorService executorService) {
		this.executorService = executorService;
	}

	public abstract void discoveryService();
}
