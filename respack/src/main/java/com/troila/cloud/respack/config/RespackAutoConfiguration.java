package com.troila.cloud.respack.config;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.protobuf.Message;
import com.troila.cloud.respack.config.settings.FilterSettings;
import com.troila.cloud.respack.core.AttrsSelector;
import com.troila.cloud.respack.core.ErrorBody;
import com.troila.cloud.respack.core.ResultPackager;
import com.troila.cloud.respack.core.impl.DefaultAttrsSelector;
import com.troila.cloud.respack.core.impl.DefaultResultPackager;
import com.troila.cloud.respack.core.impl.proto.ProtoResultPackager;
import com.troila.cloud.respack.filter.ResultPackFilter;
import com.troila.cloud.respack.filter.converter.ResultPackConverter;
import com.troila.cloud.respack.filter.converter.packconverter.JsonResultPackConverter;
import com.troila.cloud.respack.filter.converter.packconverter.ProtoResultPackConverter;

@Configuration
@EnableConfigurationProperties({ FilterSettings.class })
@ConditionalOnClass({ ResultPackFilter.class })
@ConditionalOnProperty(name = "respack.filter.settings.enable", havingValue = "true", matchIfMissing = true)
public class RespackAutoConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(RespackAutoConfiguration.class);

	/**
	 * 配置selectorAttrs的bean。用于获取response的信息。这些信息可以用于结果集的包装中
	 * 
	 * @author haodonglei
	 *
	 */
	@Configuration
	@ConditionalOnMissingBean(value = AttrsSelector.class)
	static class InnerConfig1 {

		@Bean
		public AttrsSelector createAttrsSelector() {
			AttrsSelector attrsSelector = new DefaultAttrsSelector();
			logger.info("配置默认response解析器");
			return attrsSelector;
		}
	}

	/**
	 * 配置默认的返回结果集包装器
	 * 
	 * @author haodonglei
	 *
	 */
	@Configuration
	@ConditionalOnMissingBean(value = ResultPackConverter.class)
	static class InnerConfig2 {

		@Bean
		public ResultPackager<JsonNode> createResultPackager() {
			ResultPackager<JsonNode> resultPackager = new DefaultResultPackager();
			logger.info("配置application/json结果集包装器");
			return resultPackager;
		}
		
		@Bean
		public ResultPackager<Message> createProtoResultPackager(){
			ResultPackager<Message> resultPackager = new ProtoResultPackager();
			logger.info("配置application/x-protobuf结果集包装器");
			return resultPackager;
		}
		
		
		@Bean
		public JsonResultPackConverter createJsonResultPackConverter() {
			return new JsonResultPackConverter(createResultPackager());
		}
		@Bean
		public ProtoResultPackConverter createProtoResultPackConverter() {
			return new ProtoResultPackConverter(createProtoResultPackager());
		}
	}

	@Configuration
	static class InnerConfig3 {

		@Autowired
		private AttrsSelector attrsSelector;

		@Autowired
		private List<ResultPackConverter> resultPackConverters;
		
		@Autowired
		private FilterSettings filterSettings;

		@Bean
		public FilterRegistrationBean<Filter> createFilter() {
			logger.info("创建结果集包装器filter");
			ResultPackFilter filter = new ResultPackFilter(attrsSelector, resultPackConverters,
					filterSettings);
			FilterRegistrationBean<Filter> register = new FilterRegistrationBean<Filter>();
			register.setFilter(filter);
			register.setOrder(filterSettings.getFilterOrder());
			return register;
		}
	}

	@RestController
	@AutoConfigureBefore({ ErrorMvcAutoConfiguration.class })
	@ConditionalOnMissingBean(ErrorController.class)
	class InnerJsonTypeErrorController implements ErrorController {

		private static final String PATH = "/error";
		private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
		@Autowired
		private ErrorAttributes errorAttributes;

		@Override
		public String getErrorPath() {
			return PATH;
		}

		@RequestMapping(path = PATH, produces = MediaType.APPLICATION_JSON)
		public ErrorBody sendError(HttpServletRequest req, HttpServletResponse res) {
			WebRequest webRequest = new ServletWebRequest(req);
			Throwable e = errorAttributes.getError(webRequest);
			LOGGER.error("", e);
			Map<String, Object> errorAttrs = errorAttributes.getErrorAttributes(webRequest, true);
			ErrorBody errorBody = new ErrorBody();
			errorBody.setMessage((String) errorAttrs.get("message"));
			if (e instanceof WebApplicationException) {
				errorBody.setStatus(((WebApplicationException) e).getResponse().getStatus());
				res.setStatus(((WebApplicationException) e).getResponse().getStatus());
			} else {
				errorBody.setStatus((Integer) errorAttrs.get("status"));
			}
			errorBody.setPath((String) errorAttrs.get("path"));
			errorBody.setTimeStamp((Date) errorAttrs.get("timestamp"));
			res.setHeader("Access-Control-Allow-Origin", "*"); // 不再spring mvc中，需要单独加上跨域
			return errorBody;

		}
	}
}
