package com.cliffe.flex.core.serialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: Cliffe
 * @Date: 2023-01-09 9:26 上午
 */
@Component
public class JavaSerialization extends Serialization {

	private static final Logger LOGGER = LoggerFactory.getLogger(JavaSerialization.class);

	public static final int serializationType = 0;

	public JavaSerialization() {
		super(serializationType, JavaSerialization.class);
	}

	@Override
	public <T> T deserialize(Class<T> clazz, byte[] bytes) {

		return null;
	}

	@Override
	public <T> byte[] serialize(T object) {
		return new byte[0];
	}


}
