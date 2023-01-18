package com.cliffe.flex.server.registry.zk;

import com.cliffe.flex.core.annotation.FlexService;
import com.cliffe.flex.core.spring.BeanFactoryUtil;
import com.cliffe.flex.core.util.IpUtil;
import com.cliffe.flex.server.FlexServerConfigurationProperties;
import com.cliffe.flex.server.registry.FlexRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Author: Cliffe
 * @Date: 2023-01-08 5:01 下午
 */
@Component
public class ZookeeperRegistry implements FlexRegistry {

	@Autowired
	private ServerZkUtil zkUtil;

	@Autowired
	private FlexServerConfigurationProperties cfgProperties;


	@Autowired
	private BeanFactoryUtil beanFactoryUtil;

	private static final Logger LOGGER = LoggerFactory.getLogger(IpUtil.class);

	@Override
	public void registryService() {
		Collection<Object> beans = beanFactoryUtil.getBeansByAnnotation(FlexService.class);
		if(beans == null || beans.isEmpty()){
			return;
		}
		zkUtil.createRootNode();
		for (Object bean : beans) {
			Class<?> interfaceClass = resolveInterfaceClass(bean);
			String serviceName = interfaceClass.getName();
			zkUtil.createPersistentNode(serviceName);
			String realIp = IpUtil.getRealIp();
			String providerNode = serviceName + "/" + realIp + ":"+ cfgProperties.getRpcServerPort();
			zkUtil.createNode(providerNode);
			if(LOGGER.isInfoEnabled()){
				LOGGER.info("FLEX: create node on Zookeeper successfully: serviceName = {}, providerNode: {}", serviceName, providerNode);
			}

		}
	}

	private Class<?> resolveInterfaceClass(Object bean) {
		FlexService flexServiceAnn = bean.getClass().getAnnotation(FlexService.class);
		if(flexServiceAnn.interfaceClass() == void.class){
			Class<?>[] interfaces = bean.getClass().getInterfaces();
			if(interfaces.length != 1){
				throw new RuntimeException(new ClassNotFoundException("Unable to determine the interface for [ " + bean.getClass() +" ], " +
						"please annotate the correct interfaceClass on @FlexService annotation."));
			}
			return interfaces[0];
		}
		return flexServiceAnn.interfaceClass();
	}

}
