package com.cliffe.flex.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author: Cliffe
 * @Date: 2023-01-08 5:14 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface FlexService {

	/**
	 * 类 Component(value = "")
	 */
	@AliasFor(annotation = Component.class)
	String value() default "";

	/**
	 * 要向注册中心注册的服务接口 class
	 */
	Class<?> interfaceClass() default void.class;

	/**
	 * 服务接口别名
	 */
	String interfaceName() default "";

}
