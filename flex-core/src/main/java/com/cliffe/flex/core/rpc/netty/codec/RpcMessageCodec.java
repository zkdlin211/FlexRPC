package com.cliffe.flex.core.rpc.netty.codec;

import com.cliffe.flex.core.exception.FlexException;
import com.cliffe.flex.core.message.Message;
import com.cliffe.flex.core.message.MessageTypeRegistrar;
import com.cliffe.flex.core.message.RpcResponseMessage;
import com.cliffe.flex.core.serialize.Serialization;
import com.cliffe.flex.core.serialize.SerializationRegistrar;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.cliffe.flex.core.message.Message.*;

/**
 * @Author: Cliffe
 * @Date: 2023-01-10 8:11 下午
 */
public class RpcMessageCodec extends ByteToMessageCodec<Message> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RpcMessageCodec.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		out.writeBytes(MAGIC_NUMBER);
		out.writeByte(VERSION);
		out.writeByte(msg.getSerializationType());
		out.writeByte(msg.getMessageType());
		out.writeInt(msg.getSequenceId());
		//无意义字节，对齐填充，让协议长度为2的n次方
		out.writeByte(0xff);
		// 对象序列化为byte数组
		byte[] bytes = SerializationRegistrar.getSerialization(msg.getSerializationType()).serialize(msg);
		//content长度
		out.writeInt(bytes.length);
		//content
		out.writeBytes(bytes);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		ByteBuf magicNumber = in.readBytes(4);
		byte version = in.readByte();
		byte serializationType = in.readByte();
		byte messageType = in.readByte();
		int sequenceId = in.readInt();
		in.readByte();
		int contentLength = in.readInt();
		System.out.println(contentLength);
		byte[] content = new byte[contentLength];
		in.readBytes(content, 0, contentLength);
		Serialization serialization = SerializationRegistrar.getSerialization(serializationType);
		if (serialization == null) {
			throw new FlexException("No support serialization type found, unable to deserialize this message. ");
		}
		Object msg = serialization.deserialize(MessageTypeRegistrar.getMessage(messageType), content);
		LOGGER.debug("magicNumber: {}, version: {}, serializationType: {}, messageType: {}, sequenceId: {}, contentLength: {}",
				magicNumber, version, serializationType, messageType, sequenceId, contentLength);
		LOGGER.debug("{}", msg);
		out.add(msg);
	}
}
