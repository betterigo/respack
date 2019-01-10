package com.troila.cloud.respack.config;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.troila.cloud.respack.config.settings.FilterSettings;
import com.troila.cloud.respack.core.AttrsSelector;
import com.troila.cloud.respack.core.ResultPackager;
import com.troila.cloud.respack.core.impl.DefaultAttrsSelector;
import com.troila.cloud.respack.core.impl.DefaultResultPackager;
import com.troila.cloud.respack.filter.ResultPackFilter;

@Configuration
@EnableConfigurationProperties({FilterSettings.class})
@ConditionalOnClass({ResultPackFilter.class})
@ConditionalOnProperty(name="respack.filter.settings.enable",havingValue="true",matchIfMissing=true)
public class RespackAutoConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(RespackAutoConfiguration.class);
	
	/**
	 * 配置selectorAttrs的bean。用于获取response的信息。这些信息可以用于结果集的包装中
	 * @author haodonglei
	 *
	 */
	@Configuration
	@ConditionalOnMissingBean(value=AttrsSelector.class)
	static class InnerConfig1{
		
		@Bean
		public AttrsSelector createAttrsSelector() {
			AttrsSelector attrsSelector = new DefaultAttrsSelector();
			logger.info("配置默认response解析器");
			return attrsSelector;
		}
	}
	
	/**
	 * 配置默认的返回结果集包装器
	 * @author haodonglei
	 *
	 */
	@Configuration
	@ConditionalOnMissingBean(value=ResultPackager.class)
	static class InnerConfig2{
		
		@Bean
		public ResultPackager createResultPackager() {
			ResultPackager resultPackager = new DefaultResultPackager();
			logger.info("配置默认结果集包装器");
			return resultPackager;
		}
	}
	
	
	@Configuration
	static class InnerConfig3{
		
		@Autowired
		private AttrsSelector attrsSelector;
		
		@Autowired
		private ResultPackager resultPackager;
		
		@Autowired
		private FilterSettings filterSettings;
		
		@Bean
		public FilterRegistrationBean<Filter> createFilter(){
			logger.info("创建结果集包装器filter");
			ResultPackFilter filter = new ResultPackFilter(attrsSelector,resultPackager,filterSettings.getIgnorePathsList());
			FilterRegistrationBean<Filter> register = new FilterRegistrationBean<Filter>();
			register.setFilter(filter);
			register.setOrder(Ordered.LOWEST_PRECEDENCE);
			return register;
		}
	}
}
