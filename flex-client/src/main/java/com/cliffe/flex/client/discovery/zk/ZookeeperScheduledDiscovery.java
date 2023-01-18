package com.cliffe.flex.client.discovery.zk;
import com.cliffe.flex.client.FlexClientConfigurationProperties;
import com.cliffe.flex.client.discovery.ScheduledDiscovery;
import com.cliffe.flex.core.provider.ServiceProvider;
import com.cliffe.flex.core.provider.ServiceProviderCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 11:58 上午
 */
@Component
//@ConditionalOnMissingBean(ScheduledDiscovery.class)
public class ZookeeperScheduledDiscovery extends ScheduledDiscovery {

	@Autowired
	private ZkClientUtil zkClientUtil;

	private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperScheduledDiscovery.class);

	@Autowired
	private ServiceProviderCache cache;

	@Autowired
	private FlexClientConfigurationProperties cfg;

	public ZookeeperScheduledDiscovery() {
		super(Executors.newScheduledThreadPool(1));
	}

	@Override
	public void discoveryService() {
		executorService.scheduleWithFixedDelay(() -> {
			LOGGER.debug("FLEX: Scheduled discovery service from registry");
			for (String serviceName : zkClientUtil.getServiceList()) {
				List<ServiceProvider> providers = zkClientUtil.getServiceInfos(serviceName);
				LOGGER.info("FLEX: detected registered service: {} with {} service providers. ", serviceName, providers.size());
				cache.put(serviceName, providers);
				zkClientUtil.subscribeZKEvent(serviceName);
			}
		}, 0, cfg.getFetchSecond(), TimeUnit.SECONDS);
	}
}
