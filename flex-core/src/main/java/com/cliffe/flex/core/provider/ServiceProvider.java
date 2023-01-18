package com.cliffe.flex.core.provider;

import java.awt.*;
import java.io.Serializable;

public class ServiceProvider implements Serializable {
    private String serviceName;
    private String serverIp;
    private int rpcPort;
    private int networkPort;
    private long timeout;
    // the weight of service provider
    private int weight;

	private static final Builder builder = new Builder();

	private ServiceProvider() {
	}

	public static Builder builder() {
		return builder;
	}

	private ServiceProvider(ServiceProvider origin){
		this.serviceName = origin.serviceName;
		this.serverIp = origin.serverIp;
		this.rpcPort = origin.rpcPort;
		this.networkPort = origin.networkPort;
		this.timeout = origin.timeout;
		this.weight = origin.weight;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getRpcPort() {
		return rpcPort;
	}

	public void setRpcPort(int rpcPort) {
		this.rpcPort = rpcPort;
	}

	public int getNetworkPort() {
		return networkPort;
	}

	public void setNetworkPort(int networkPort) {
		this.networkPort = networkPort;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getIpWithPort(){
		return serverIp+":"+rpcPort;
	}

	public static class Builder {
		private ServiceProvider serviceProvider;

		public Builder() {
			this.serviceProvider = new ServiceProvider();
		}

		public Builder serviceName(String serviceName) {
			serviceProvider.serviceName = serviceName;
			return this;
		}

		public Builder serverIp(String serverIp) {
			serviceProvider.serverIp = serverIp;
			return this;
		}

		public Builder rpcPort(int rpcPort) {
			serviceProvider.rpcPort = rpcPort;
			return this;
		}

		private Builder networkPort(int networkPort){
			serviceProvider.networkPort = networkPort;
			return this;
		}

		private Builder timeout(int timeout){
			serviceProvider.timeout = timeout;
			return this;
		}

		private Builder weight(int weight){
			serviceProvider.weight = weight;
			return this;
		}

		public ServiceProvider build() {
			return new ServiceProvider(serviceProvider);
		}
		
	}
}