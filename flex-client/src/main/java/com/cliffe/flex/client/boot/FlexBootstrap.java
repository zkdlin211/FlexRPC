package com.cliffe.flex.client.boot;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 11:46 上午
 */
@Component
@DependsOn({"beanFactoryUtil", "serializationRegistrar"})
public class FlexBootstrap implements InitializingBean {

	@Autowired
	private FlexClientRunner flexClientRunner;

	@Override
	public void afterPropertiesSet() throws Exception {
		flexClientRunner.run();
	}
}
