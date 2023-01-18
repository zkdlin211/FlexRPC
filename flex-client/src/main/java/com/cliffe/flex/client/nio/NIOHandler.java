package com.cliffe.flex.client.nio;

@FunctionalInterface
public interface NIOHandler {
	void handleResponse(Object result);
}