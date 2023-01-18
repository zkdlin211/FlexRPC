package com.cliffe.flex.core.rpc.netty.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.StandardCharsets;

/**
 * @Author: Cliffe
 * @Date: 2022-12-25 12:24 下午
 */
public class FlexProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {

	public static final int maxFrameLength = Integer.MAX_VALUE;

	public static final int lengthFieldOffset = 12;

	public static final int lengthFieldLength = 4;

	public static final int lengthAdjustment = 0;

	public static final int initialBytesToStrip = 0;

	public byte[] magicNumber = "FLEX".getBytes(StandardCharsets.UTF_8);

	public int version = 1;

    public FlexProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public FlexProtocolFrameDecoder() {
        this(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment,initialBytesToStrip);
    }
}
