package com.cliffe.flex.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 12:04 下午
 */
@ConfigurationProperties(prefix = "flex.client.zk")
public class FlexZkClientConfigurationProperties extends FlexClientRegistrationConfigurationProperties{

	private String root;

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	@Override
	public String getPath() {
		return root;
	}

	@Override
	public void setPath() {
		path = root;
	}
}
