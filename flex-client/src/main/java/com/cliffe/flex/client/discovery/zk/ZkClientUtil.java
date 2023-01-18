package com.cliffe.flex.client.discovery.zk;

import com.cliffe.flex.client.FlexZkClientConfigurationProperties;
import com.cliffe.flex.core.provider.ServiceProvider;
import com.cliffe.flex.core.provider.ServiceProviderCache;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 12:00 下午
 */
@Component
public class ZkClientUtil {
	@Autowired
	private FlexZkClientConfigurationProperties zkCfgProperties;

	@Autowired
	private ZkClient zkClient;

	@Autowired
	private ServiceProviderCache cache;

	/**
	 * 服务订阅接口
	 * @param serviceName
	 */
	public void subscribeZKEvent(String serviceName) {
		// 1. 组装服务节点信息
		String path = zkCfgProperties.getRoot() + "/" + serviceName;
		// 2. 订阅服务节点（监听节点变化）
		zkClient.subscribeChildChanges(path, new IZkChildListener() {
			@Override
			public void handleChildChange(String parentPath, List<String> list) throws Exception {
				// 3. 判断获取的节点信息，是否为空
				if (!CollectionUtils.isEmpty(list)) {
					// 4. 将服务端获取的信息， 转换为服务记录对象
					List<ServiceProvider> providerServices = convertToProviderService(serviceName, list);
					// 5. 更新缓存信息 记得要改
					cache.update(serviceName,providerServices);
				}
			}
		});
	}


	/**
	 * 获取所有服务列表：所有的服务接口信息
	 * @return
	 */
	public List<String> getServiceList() {
		String path = zkCfgProperties.getRoot();
		return zkClient.getChildren(path);
	}

	/**
	 *  根据服务名称获取服务节点完整信息
	 * @param serviceName
	 * @return
	 */
	public List<ServiceProvider> getServiceInfos(String serviceName) {
		String path = zkCfgProperties.getRoot() + "/" + serviceName;
		List<String> children = zkClient.getChildren(path);
		return convertToProviderService(serviceName,children);
	}

	/**
	 * 将拉取的服务节点信息转换为服务记录对象
	 *
	 * @param serviceName
	 * @param children
	 * @return
	 */
	private List<ServiceProvider> convertToProviderService(String serviceName, List<String> children) {
		if (CollectionUtils.isEmpty(children)) {
			return new ArrayList<>(0);
		}
		// 将服务节点信息转换为服务记录对象
		//TODO create MetaData instead of using String
		return children.stream().map(v -> {
			String[] serviceInfos = v.split(":");
			return ServiceProvider.builder()
					.serviceName(serviceName)
					.serverIp(serviceInfos[0])
					.rpcPort(Integer.parseInt(serviceInfos[1]))
					.build();
		}).collect(Collectors.toList());
	}
}
