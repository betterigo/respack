package io.github.betterigo.respack.core;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

/**
 * @Description 结果包装器
 * @author haodonglei
 * @date 2022年2月18日 上午10:07:41     
 * @Copyright 2022 Inc. All rights reserved.
 */
public interface ResultPackager<T> {
	
	T packResult(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response);
	
}
