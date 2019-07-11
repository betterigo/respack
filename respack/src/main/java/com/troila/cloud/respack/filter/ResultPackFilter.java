package com.troila.cloud.respack.filter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.troila.cloud.dev.common.exception.BaseErrorException;
import com.troila.cloud.respack.config.settings.FilterSettings;
import com.troila.cloud.respack.core.AttrsSelector;
import com.troila.cloud.respack.core.RespAttrs;
import com.troila.cloud.respack.filter.converter.ResultPackConverter;

/**
 * 该filter将负责包装返回结果
 * 
 * @author haodonglei
 *
 */
public class ResultPackFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(ResultPackFilter.class);

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
		if (!matchUri(uri)) {
			ResponseWrapper wrapper = new ResponseWrapper(response, filterSettings.getMaxCache());
			RespAttrs respAttrs = null;
			try {
				filterChain.doFilter(request, wrapper);
				// 后续操作
				// 1.获取response中的属性信息
				respAttrs = attrsSelector.selectResponseAtts(wrapper);
			} catch (Exception e) {
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
			String contentType = request.getHeader("Accept");
			if(contentType != null && contentType.equals("*/*")) {
				contentType = request.getContentType();
			}
			for (ResultPackConverter converter : this.resultPackConverters) {
				if (converter.canPack(contentType)) {
					converter.packResult(wrapper, response, respAttrs);
					hasPack = true;
					break;
				}
			}
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
