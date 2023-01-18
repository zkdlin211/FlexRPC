package com.cliffe.flex.core.serialize;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Cliffe
 * @Date: 2023-01-09 8:52 上午
 */
@Component
public class SerializationRegistrar implements ApplicationContextAware {

	private static final Map<Integer, Serialization> serializationRegistryMap = new HashMap<>();
	private static final Map<Class<? extends Serialization>, Integer> serializationNoMap = new HashMap<>();

	public static void registrySerializationType(int number, Serialization serialization){
		if(serializationRegistryMap.containsKey(number)){
			throw new RuntimeException("serializationTypeRegistryMap contains " +
					"another class with same identification number [ " + number + " ], " +
					"consider reassign a new number to [ " + serialization.getClass().getName() + " ].");
		}
		serializationRegistryMap.put(number, serialization);
		serializationNoMap.put(serialization.getClass(), number);
	}
	
	public static Serialization getSerialization(int no){
		return serializationRegistryMap.get(no);
	}
	public static Integer getSerializationNo(Class<? extends Serialization> clazz){
		return serializationNoMap.get(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		for (Serialization serialization : context.getBeansOfType(Serialization.class).values()) {
			SerializationRegistrar.registrySerializationType(serialization.getSerializationType(), serialization);
		}
	}
}
