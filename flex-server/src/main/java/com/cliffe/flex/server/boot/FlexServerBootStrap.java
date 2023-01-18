package com.cliffe.flex.server.boot;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: Cliffe
 * @Date: 2023-01-08 4:41 下午
 */
@Component
@DependsOn({"beanFactoryUtil", "serializationRegistrar"})
public class FlexServerBootStrap implements InitializingBean {

	@Autowired
	FlexServerRunner flexServerRunner;

	@Override
	public void afterPropertiesSet() throws Exception {
		flexServerRunner.run();
	}

}
