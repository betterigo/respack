package io.github.betterigo.respack.dec.base;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;

import io.github.betterigo.respack.exception.BaseErrorException;

/**
 * 默认结果包装实现接口，当自定义实现时，该配置失效
 * @author hao
 *
 */
public class DefaultRespackResultResolver implements RespackResultResolver{

	@Override
	public Object pack(Object body, MethodParameter returnType, MediaType selectedContentType, ServerHttpRequest request, ServerHttpResponse response) {
		ServletServerHttpResponse servletServerHttpResponse = (ServletServerHttpResponse) response;
        ResultPackBody<Object> resultPackBody = new ResultPackBody<>();
        resultPackBody.setData(body);
        resultPackBody.setStatus(servletServerHttpResponse.getServletResponse().getStatus());
        return resultPackBody;
	}

	@Override
	public Object handleException(BaseErrorException e) {
		ResultPackBody<Object> resultPackBody = new ResultPackBody<>();
        resultPackBody.setMessage(e.getMessage());
        resultPackBody.setErr_code(e.getErrorCode());
        resultPackBody.setStatus(200);//默认200
        return resultPackBody;
	}

}
