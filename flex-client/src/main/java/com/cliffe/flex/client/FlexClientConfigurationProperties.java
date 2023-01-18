package com.cliffe.flex.client;

import com.cliffe.flex.core.serialize.JsonSerialization;
import com.cliffe.flex.core.serialize.Serialization;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 5:55 p.m.
 */
@ConfigurationProperties(prefix = "flex.client")
public class FlexClientConfigurationProperties {

	private boolean enableFlexClients = true;

	private String proxyType = "cglib";

	private String loggingLevel = "INFO";

	/**
	 * The interval between fetching and refresh the list of available provider services
	 * from the registry.
	 * Unit: second
	 */
	private int fetchSecond = 30;

	private int rpcServerPort;

	private String rpcServerHostname = "localhost";

	private String lbStrategy = "random";

	private Class<? extends Serialization> serializationType = JsonSerialization.class;

	public String getLbStrategy() {
		return lbStrategy;
	}

	public void setLbStrategy(String lbStrategy) {
		this.lbStrategy = lbStrategy;
	}

	public Class<? extends Serialization> getSerializationType() {
		return serializationType;
	}

	public void setSerializationType(Class<? extends Serialization> serializationType) {
		this.serializationType = serializationType;
	}

	public int getFetchSecond() {
		return fetchSecond;
	}

	public void setFetchSecond(int fetchSecond) {
		this.fetchSecond = fetchSecond;
	}

	public String getRpcServerHostname() {
		return rpcServerHostname;
	}

	public boolean isEnableFlexClients() {
		return enableFlexClients;
	}

	public void setEnableFlexClients(boolean enableFlexClients) {
		this.enableFlexClients = enableFlexClients;
	}

	public void setRpcServerHostname(String rpcServerHostname) {
		this.rpcServerHostname = rpcServerHostname;
	}

	public int getRpcServerPort() {
		return rpcServerPort;
	}

	public void setRpcServerPort(int rpcServerPort) {
		this.rpcServerPort = rpcServerPort;
	}

	public String getLoggingLevel() {
		return loggingLevel;
	}

	public void setLoggingLevel(String loggingLevel) {
		this.loggingLevel = loggingLevel;
	}

	public String getProxyType() {
		return proxyType;
	}

	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}
}
