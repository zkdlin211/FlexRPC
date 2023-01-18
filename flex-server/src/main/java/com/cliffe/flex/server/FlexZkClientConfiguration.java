package com.cliffe.flex.server;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Cliffe
 * @Date: 2023-01-08 7:24 下午
 */
@Configuration
public class FlexZkClientConfiguration {

	@Autowired
	private FlexServerZkRegistrationConfigurationProperties serverConfiguration;

	@Bean(name = "flexClientZkClient")
	public ZkClient zkClient() {
		return new ZkClient(serverConfiguration.getAddr(), serverConfiguration.getConnectTimeout());
	}

}
