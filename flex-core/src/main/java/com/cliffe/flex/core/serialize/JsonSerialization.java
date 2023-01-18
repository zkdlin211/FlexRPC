package com.cliffe.flex.core.serialize;

import com.google.gson.*;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Cliffe
 * @Date: 2023-01-12 6:48 p.m.
 */
@Component
public class JsonSerialization extends Serialization {

	public static final int serializationType = 1;
	public JsonSerialization() {
		super(serializationType, JsonSerialization.class);
	}

	@Override
	public <T> T deserialize(Class<T> clazz, byte[] bytes) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCodec()).create();
		return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), clazz);
	}

	@Override
	public <T> byte[] serialize(T object) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCodec()).create();
		return gson.toJson(object).getBytes(StandardCharsets.UTF_8);
	}

	static class ClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {

		@Override
		public Class<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			try {
				return Class.forName(jsonElement.getAsString());
			} catch (ClassNotFoundException e) {
				throw new JsonParseException(e);
			}
		}

		@Override
		public JsonElement serialize(Class<?> clazz, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(clazz.getName());
		}
	}
}
