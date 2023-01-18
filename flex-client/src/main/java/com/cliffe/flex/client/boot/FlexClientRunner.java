package com.cliffe.flex.client.boot;
import com.cliffe.flex.client.boot.netty.NettyFlexClient;
import com.cliffe.flex.client.discovery.ScheduledDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 11:51 上午
 */
@Component
public class FlexClientRunner {

	@Autowired
	private ScheduledDiscovery scheduledDiscovery;

	@Autowired
	private NettyFlexClient nettyFlexClient;

	/**
	 * 服务发现：在注册中心获取
	 */
	public void run(){
		scheduledDiscovery.discoveryService();
	}
}
