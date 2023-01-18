package com.cliffe.flex.client.annotation;

import com.cliffe.flex.client.constant.Strategy;
import com.cliffe.flex.client.nio.NIOHandler;

import java.lang.annotation.*;

/**
 * @Author: Cliffe
 * @Date: 2023-01-12 1:28 p.m.
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NIO {
	Class<? extends NIOHandler> nioHandler();

	/**
	 * fallback strategy
	 * default not processed
	 */
	Strategy fallbackStrategy() default Strategy.NULL;

	Class<?> fallbackClass() default Object.class;

	String fallbackMethod() default "";

}
