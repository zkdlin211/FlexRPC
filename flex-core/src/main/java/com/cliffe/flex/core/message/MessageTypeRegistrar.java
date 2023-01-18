package com.cliffe.flex.core.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Cliffe
 * @Date: 2023-01-10 6:44 下午
 */
public class MessageTypeRegistrar {

	private static final Map<Integer, Class<? extends Message>> messageTypeMap = new HashMap<>();
	private static final Set<Integer> distinctSet = new HashSet<>();

	static {
		messageTypeMap.put(RpcRequestMessage.RpcRequestMessageType, RpcRequestMessage.class);
		messageTypeMap.put(RpcResponseMessage.RpcResponseMessageType, RpcResponseMessage.class);
	}

	public static void registryMessageType(int number, Class<? extends Message> clazz){
		if(!distinctSet.add(number)){
			throw new RuntimeException("serializationTypeRegistryMap contains " +
					"another class with same identification number [ " + number + " ], " +
					"consider reassign a new number to [ " + clazz.getName() + " ].");
		}
		messageTypeMap.put(number, clazz);
	}

	public static Class<? extends Message> getMessage(int no){
		return messageTypeMap.get(no);
	}
}
