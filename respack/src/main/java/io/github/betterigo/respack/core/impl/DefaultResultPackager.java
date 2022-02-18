package io.github.betterigo.respack.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import io.github.betterigo.respack.config.settings.PackSettings;
import io.github.betterigo.respack.core.PatternUtil;
import io.github.betterigo.respack.core.ResultPackBody;

/**
 * @Description 默认的结果集包装器
 * @author haodonglei
 * @date 2022年2月18日 上午10:54:47
 * @Copyright 2022 Inc. All rights reserved.
 */
public class DefaultResultPackager extends AbstractResultPackager<ResultPackBody<?>> {

	private List<MediaType> supportTypes;
	
	private List<String> ignorePaths;

	public DefaultResultPackager(PackSettings packSettings) {
		super();
		supportTypes = new ArrayList<>();
		supportTypes.add(new MediaType("application", "json"));
		supportTypes = Collections.unmodifiableList(supportTypes);
		String[] ignorePathsArray = StringUtils.commaDelimitedListToStringArray(packSettings.getIgnorePaths());
		ignorePaths = Lists.newArrayList(ignorePathsArray);
	}

	@Override
	protected boolean canPack(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		String uri = request.getURI().getPath();
		if(matchUri(uri)) {
			return false;
		}
		for (MediaType mType : supportTypes) {
			if (mType.includes(selectedContentType)) {
				return true;
			}
		}
		return false;
	}

	@Override
	ResultPackBody<?> packResult0(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		ServletServerHttpResponse servletServerHttpResponse = (ServletServerHttpResponse) response;
		ResultPackBody<Object> resultPackBody = new ResultPackBody<>();
		resultPackBody.setData(body);
		resultPackBody.setStatus(servletServerHttpResponse.getServletResponse().getStatus());
		return resultPackBody;
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
