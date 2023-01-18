package com.cliffe.flex.core.message;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public abstract class Message implements Serializable {

	private int SerializationType;

	public static final byte[] MAGIC_NUMBER = "flex".getBytes(StandardCharsets.UTF_8);

	public static final int VERSION = 1;

    private int sequenceId;

    private final int messageType;

    public abstract int getMessageType();

	public abstract Class<?> getMessageClass();

	public int getSerializationType() {
		return SerializationType;
	}

	public void setSerializationType(int serializationType) {
		SerializationType = serializationType;
	}

	public Message(int messageType) {
		this.messageType = messageType;
	}

	public int getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
	}

}
