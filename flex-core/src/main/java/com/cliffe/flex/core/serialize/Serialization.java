package com.cliffe.flex.core.serialize;

/**
 * @Author: Cliffe
 * @Date: 2023-01-09 8:52 上午
 */
public abstract class Serialization {

	private final int serializationType;

	public Serialization(int serializationType, Class<? extends Serialization> clazz) {
		this.serializationType = serializationType;
	}

	public int getSerializationType() {
		return serializationType;
	}

	public abstract <T> T deserialize(Class<T> clazz, byte[] bytes);

	public abstract <T> byte[] serialize(T object);

}
