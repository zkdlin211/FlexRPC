package com.cliffe.flex.client;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: Cliffe
 * @Date: 2023-01-12 11:47 a.m.
 */
public abstract class FlexClientRegistrationConfigurationProperties {

	protected String path;

	protected String addr;

	protected String basePackage;


	protected int connectTimeout;


	public abstract String getPath();

	public abstract void setPath();

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}


	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
}
