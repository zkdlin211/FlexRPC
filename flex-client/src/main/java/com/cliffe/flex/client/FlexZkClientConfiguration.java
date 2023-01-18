package com.cliffe.flex.client;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Cliffe
 * @Date: 2023-01-08 7:24 下午
 */
@Configuration
public class FlexZkClientConfiguration {

	@Autowired
	private FlexZkClientConfigurationProperties zkClientConfigurationProperties;

	@Bean
	public ZkClient zkClient() {
		return new ZkClient(zkClientConfigurationProperties.getAddr(),
				zkClientConfigurationProperties.getConnectTimeout());
	}

}
