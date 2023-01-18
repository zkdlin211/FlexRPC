package com.cliffe.flex.client.annotation;

import java.lang.annotation.*;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 2:06 p.m.
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FlexRpc {

	boolean enableCache() default false;

	String interfaceName() default "";

	String lbStrategy() default "random";

}
