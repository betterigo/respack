package io.github.betterigo.respack.config;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import io.github.betterigo.respack.config.settings.FilterSettings;
import io.github.betterigo.respack.filter.converter.ResultPackConverter;
import io.github.betterigo.respack.filter.converter.packconverter.JsonResultPackConverter;
import io.github.betterigo.respack.filter.converter.packconverter.ProtoResultPackConverter;
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

import io.github.betterigo.respack.core.AttrsSelector;
import io.github.betterigo.respack.core.ErrorBody;
import io.github.betterigo.respack.core.PackEntity;
import io.github.betterigo.respack.core.ResultPackager;
import io.github.betterigo.respack.core.impl.ByteResultPackager;
import io.github.betterigo.respack.core.impl.DefaultAttrsSelector;
import io.github.betterigo.respack.core.impl.StringResultPackager;
import io.github.betterigo.respack.core.impl.proto.ProtoResultPackager;
import io.github.betterigo.respack.filter.ResultPackFilter;

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
		public ResultPackager<byte[],byte[]> createJsonResultPackager() {
//			ResultPackager<JsonNode,PackEntity> resultPackager = new DefaultResultPackager();
			ByteResultPackager resultPackager = new ByteResultPackager();
			logger.info("配置application/json结果集包装器");
			return resultPackager;
		}
		
		
		@Bean
		public StringResultPackager createStringResultPackager() {
			StringResultPackager resultPackager = new StringResultPackager();
			logger.info("配置默认结果集包装器");
			return resultPackager;
		}
		
		@Bean
		public ResultPackager<byte[],PackEntity> createProtoResultPackager(){
			ResultPackager<byte[],PackEntity> resultPackager = new ProtoResultPackager();
			logger.info("配置application/x-protobuf结果集包装器");
			return resultPackager;
		}
		
//		@Bean
//		public StringResultPackConverter createStringResultPackConverter() {
//			return new StringResultPackConverter(createStringResultPackager());
//		}
//		
		@Bean
		public JsonResultPackConverter createJsonResultPackConverter(StringResultPackager stringResultPackager) {
			JsonResultPackConverter converter = new JsonResultPackConverter(createJsonResultPackager());
			converter.setDefaultResultPackager(stringResultPackager);
			return converter;
		}
		@Bean
		public ProtoResultPackConverter createProtoResultPackConverter(StringResultPackager stringResultPackager) {
			ProtoResultPackConverter converter = new ProtoResultPackConverter(createProtoResultPackager());
			converter.setDefaultResultPackager(stringResultPackager);
			return converter;
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
