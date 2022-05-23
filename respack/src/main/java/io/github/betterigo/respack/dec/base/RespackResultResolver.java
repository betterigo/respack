package io.github.betterigo.respack.dec.base;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import io.github.betterigo.respack.exception.BaseErrorException;

/**
 * 结果包装实现接口
 * @author hao
 *
 */
public interface RespackResultResolver {

	/**
	 * 正常返回调用
	 * @param body
	 * @param returnType
	 * @param selectedContentType
	 * @return
	 */
	Object pack(Object body, MethodParameter returnType, MediaType selectedContentType, ServerHttpRequest request, ServerHttpResponse response);
	
	/**
	 * 异常返回是调用
	 * @param e
	 * @return
	 */
	Object handleException(BaseErrorException e);
	
}
