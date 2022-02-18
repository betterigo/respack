package io.github.betterigo.respack.core.impl;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import io.github.betterigo.respack.core.ResultPackager;

/**
 * @Description 结果包装抽象类，添加了canpack方法
 * @author haodonglei
 * @date 2022年2月18日 上午11:04:04
 * @param <T>
 * @Copyright 2022 Inc. All rights reserved.
 */
public abstract class AbstractResultPackager<T> implements ResultPackager<Object> {

	@Override
	public Object packResult(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if (canPack(body, returnType, selectedContentType, selectedConverterType, request, response)) {
			return packResult0(body, returnType, selectedContentType, selectedConverterType, request, response);
		}
		return body;
	}

	protected abstract boolean canPack(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response);

	abstract T packResult0(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response);
}
