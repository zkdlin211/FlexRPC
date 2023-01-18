package com.cliffe.flex.client.cluster.lb;

import com.cliffe.flex.core.exception.FlexException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Cliffe
 * @Date: 2023-01-15 3:23 p.m.
 */
@Component
public class LoadBalanceSelectorRegistry implements ApplicationContextAware {

	private static final Map<String, LoadBalanceSelector> loadBalanceSelectorMap = new HashMap<>();


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		for (LoadBalanceSelector selector : applicationContext.getBeansOfType(LoadBalanceSelector.class).values()) {
			if(loadBalanceSelectorMap.containsKey(selector.strategy())){
				throw new FlexException(selector.strategy()+" has been used, please rename your loadBalanceSelector!");
			}
			loadBalanceSelectorMap.put(selector.strategy(), selector);
		}
	}

	public LoadBalanceSelector get(String strategy){
		if(!loadBalanceSelectorMap.containsKey(strategy)){
			throw new FlexException("such loadBalanceSelector bean [" + strategy + "] does not exist, " +
					"please ensure it's registered into beanFactory!");
		}
		return loadBalanceSelectorMap.get(strategy);
	}
}
