package com.cliffe.flex.client.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 2:11 p.m.
 */
public class SequenceIdGenerator {

	private SequenceIdGenerator(){
	}

	private static final AtomicInteger ID = new AtomicInteger();

	public static int nextId(){return ID.incrementAndGet();}
}
