package io.github.betterigo.respack.dec.base;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.betterigo.respack.exception.BaseErrorException;

/**
 * 默认结果包装实现接口，当自定义实现时，该配置失效
 * @author hao
 *
 */
public class DefaultRespackResultResolver implements RespackResultResolver{

	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public Object pack(Object body, MethodParameter returnType, MediaType selectedContentType, ServerHttpRequest request, ServerHttpResponse response) {
		ServletServerHttpResponse servletServerHttpResponse = (ServletServerHttpResponse) response;
        ResultPackBody<Object> resultPackBody = new ResultPackBody<>();
        resultPackBody.setData(body);
        resultPackBody.setStatus(servletServerHttpResponse.getServletResponse().getStatus());
        if(selectedContentType.isCompatibleWith(MediaType.TEXT_PLAIN) || returnType.getGenericParameterType().equals(String.class)) {            	
        	try {
        		return mapper.writeValueAsString(resultPackBody);
        	} catch (JsonProcessingException e) {
        		e.printStackTrace();
        	}
        }
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
