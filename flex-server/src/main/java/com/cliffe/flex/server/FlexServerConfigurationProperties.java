package com.cliffe.flex.server;

import com.cliffe.flex.core.serialize.JsonSerialization;
import com.cliffe.flex.core.serialize.Serialization;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Cliffe
 * @Date: 2023-01-12 6:42 p.m.
 */
@ConfigurationProperties(prefix = "flex.server")
public class FlexServerConfigurationProperties {


	private boolean enableFlexServer = true;

	private int rpcServerPort;

	private String rpcServerHostname = "localhost";

	private Class<? extends Serialization> serializationType = JsonSerialization.class;

	public boolean isEnableFlexServer() {
		return enableFlexServer;
	}

	public void setEnableFlexServer(boolean enableFlexServer) {
		this.enableFlexServer = enableFlexServer;
	}

	public int getRpcServerPort() {
		return rpcServerPort;
	}

	public void setRpcServerPort(int rpcServerPort) {
		this.rpcServerPort = rpcServerPort;
	}

	public String getRpcServerHostname() {
		return rpcServerHostname;
	}

	public void setRpcServerHostname(String rpcServerHostname) {
		this.rpcServerHostname = rpcServerHostname;
	}

	public Class<? extends Serialization> getSerializationType() {
		return serializationType;
	}

	public void setSerializationType(Class<? extends Serialization> serializationType) {
		this.serializationType = serializationType;
	}
}
