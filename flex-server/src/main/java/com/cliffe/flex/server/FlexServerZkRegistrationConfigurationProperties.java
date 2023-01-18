package com.cliffe.flex.server;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Cliffe
 * @Date: 2023-01-08 5:05 下午
 */
@ConfigurationProperties(prefix = "flex.server.zk")
public class FlexServerZkRegistrationConfigurationProperties {

	private String root;

	private String addr;

	private int connectTimeout;

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}


	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}
}
