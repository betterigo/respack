package io.github.betterigo.respack.filter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.betterigo.respack.config.settings.FilterSettings;
import io.github.betterigo.respack.exception.BaseErrorException;
import io.github.betterigo.respack.filter.converter.ResultPackConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.betterigo.respack.core.AttrsSelector;
import io.github.betterigo.respack.core.RespAttrs;

/**
 * 该filter将负责包装返回结果
 * 
 * @author haodonglei
 *
 */
public class ResultPackFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(ResultPackFilter.class);
	/**
	 * 当没有指定格式的时候，默认按照json格式处理
	 */
	private static final String DefaultContentType = "application/json";

	private AttrsSelector attrsSelector;

	private List<ResultPackConverter> resultPackConverters;

	private List<String> ignorePaths;

	private FilterSettings filterSettings;
	

	ObjectMapper mapper = new ObjectMapper();

	public ResultPackFilter(AttrsSelector attrsSelector, List<ResultPackConverter> resultPackConverters,
			FilterSettings filterSettings) {
		super();
		this.attrsSelector = attrsSelector;
		this.resultPackConverters = resultPackConverters;
		this.filterSettings = filterSettings;
		if (filterSettings != null) {
			this.ignorePaths = filterSettings.getIgnorePathsList();
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		
		String contentType = request.getHeader("Accept");
		if(contentType != null && contentType.equals("*/*")) {
			contentType = request.getContentType();
			// 当Accept为Java默认的接收类型，前端又没有给content-type的值时，默认按照json格式处理
			if(StringUtils.isEmpty(contentType)) {
				contentType = DefaultContentType;
			}
		}
		ResultPackConverter targetConverter = null;
		for (ResultPackConverter converter : this.resultPackConverters) {
			if (converter.canPack(contentType)) {
				targetConverter = converter;
				break;
			}
		}
		
		if (!matchUri(uri) && targetConverter!=null) {
			ResponseWrapper wrapper = new ResponseWrapper(response, filterSettings.getMaxCache());
			RespAttrs respAttrs = null;
			try {
				filterChain.doFilter(request, wrapper);
				// 后续操作
				// 1.获取response中的属性信息
				respAttrs = attrsSelector.selectResponseAtts(wrapper);
			} catch (Exception e) {
				//需要设置返回值的contentType
				response.setContentType("application/json;charset=utf-8");
				if (e.getCause() instanceof BaseErrorException) {
					BaseErrorException be = (BaseErrorException) e.getCause();
					respAttrs = initRespAttrs(uri, wrapper, be);
				} else if (e instanceof BaseErrorException) {
					BaseErrorException be = (BaseErrorException) e;
					respAttrs = initRespAttrs(uri, wrapper, be);
				} else {
					throw e;
				}
			}
			boolean hasPack = false;
			targetConverter.packResult(wrapper, response, respAttrs);
			hasPack = true;
			// 获取返回的消息体 目前只处理application/json,application/x-protobuf类型
			if (!hasPack) {
				Class<?> clazz = response.getClass();
				if (clazz.getName().equals("io.undertow.servlet.spec.HttpServletResponseImpl")) {// 处理undertow
					try {
						Field f = clazz.getDeclaredField("treatAsCommitted");
						f.setAccessible(true);
						boolean v = f.getBoolean(response);
						if (!v) {
							wrapper.flushCacheStream();
						}
					} catch (SecurityException e) {
						logger.error("", e);
					} catch (IllegalArgumentException e) {
						logger.error("", e);
					} catch (NoSuchFieldException e) {
						logger.error("", e);
					} catch (IllegalAccessException e) {
						logger.error("", e);
					}
				} else {
					wrapper.flushCacheStream();
				}
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

	private RespAttrs initRespAttrs(String uri, ResponseWrapper wrapper, BaseErrorException be) {
		RespAttrs respAttrs;
		logger.info("path:{} => error_code:{}", uri, be.getErrorCode());
		respAttrs = attrsSelector.selectExceptionAtts(wrapper, be);
		return respAttrs;
	}

	private boolean matchUri(String uri) {
		for (String uriPattern : ignorePaths) {
			if (PatternUtil.match(uriPattern, uri)) {
				return true;
			}
		}
		return false;
	}
}
