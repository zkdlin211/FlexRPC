package com.cliffe.flex.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Cliffe
 * @Date: 2023-01-08 5:38 下午
 */
@Component
public class BeanFactoryUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	public <T> T getBean(Class<T> clazz){
		if(context == null){
			return null;
		}
		return context.getBean(clazz);
	}

	public Collection<Object> getBeansByAnnotation(Class<? extends Annotation> annotation){
		if(context == null){
			return null;
		}
		return context.getBeansWithAnnotation(annotation).values();
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
