package com.cliffe.flex.client.boot;

import com.cliffe.flex.client.annotation.FlexRpc;
import com.cliffe.flex.core.message.Message;
import com.cliffe.flex.core.message.RpcRequestMessage;
import io.netty.channel.Channel;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Cliffe
 * @Date: 2023-01-12 11:25 a.m.
 */
public interface FlexClient {


	Object send(Message message, Method method, FlexRpc flexRpc);
}
