package com.cliffe.flex.client;

import com.cliffe.flex.core.provider.DefaultServiceProviderCache;
import com.cliffe.flex.core.provider.ServiceProvider;
import com.cliffe.flex.core.provider.ServiceProviderCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

import java.util.List;

/**
 * @Author: Cliffe
 * @Date: 2023-01-11 11:52 上午
 */
@ConditionalOnProperty(prefix = "flex.client", name = "enableFlexClients", havingValue = "true", matchIfMissing = true)
@ConditionalOnMissingBean(FlexClientAutoConfiguration.class)
@EnableConfigurationProperties({FlexZkClientConfigurationProperties.class, FlexClientConfigurationProperties.class})
@ComponentScans({
		@ComponentScan("com.cliffe.flex.client"),
		@ComponentScan("com.cliffe.flex.core.serialize"),
		@ComponentScan("com.cliffe.flex.core.spring")
})
@Configuration
public class FlexClientAutoConfiguration {

	@Bean(name = "flexClientLoadingCache")
//	@ConditionalOnBean(DefaultServiceProviderCache.class)
	public LoadingCache<String, List<ServiceProvider>> loadingCache() {
		return CacheBuilder.newBuilder()
				.build(new CacheLoader<String, List<ServiceProvider>>() {
					@Override
					public List<ServiceProvider> load(String key) throws Exception {
						//在这里可以初始化加载数据的缓存信息，读取数据库中信息或者是加载文件中的某些数据信息
						return null;
					}
				});
	}

	@Bean(name = "flexClientServiceProviderCache")
	public ServiceProviderCache serviceProviderCache(LoadingCache<String, List<ServiceProvider>> loadingCache){
		return new DefaultServiceProviderCache(loadingCache);
	}
}
