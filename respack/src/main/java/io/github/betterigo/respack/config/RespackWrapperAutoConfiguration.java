package io.github.betterigo.respack.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import io.github.betterigo.respack.dec.annotation.WithoutPack;
import io.github.betterigo.respack.dec.base.ResultPackBody;
import io.github.betterigo.respack.exception.BaseErrorException;

@Configuration
@ConditionalOnProperty(name = "respack.filter.settings.enable", havingValue = "true", matchIfMissing = true)
public class RespackWrapperAutoConfiguration {
	
	/**
	 * @Description 结果集装饰器
	 * @author haodonglei
	 * @date 2022年2月16日 下午3:59:20     
	 * @Copyright 2022 Inc. All rights reserved.
	 */
	@ControllerAdvice(annotations = RestController.class)
	static class ResultPackWrapper implements ResponseBodyAdvice<Object>{

		  private List<MediaType> supportTypes;

		    @PostConstruct
		    void init(){
		        supportTypes = new ArrayList<>();
		        supportTypes.add(new MediaType("application","json"));
		        supportTypes = Collections.unmodifiableList(supportTypes);
		    }

		    /**
		     * Whether this component supports the given controller method return type
		     * and the selected {@code HttpMessageConverter} type.
		     *
		     * @param returnType    the return type
		     * @param converterType the selected converter type
		     * @return {@code true} if {@link #beforeBodyWrite} should be invoked;
		     * {@code false} otherwise
		     */
		    @Override
		    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		        return true;
		    }

		    /**
		     * Invoked after an {@code HttpMessageConverter} is selected and just before
		     * its write method is invoked.
		     *
		     * @param body                  the body to be written
		     * @param returnType            the return type of the controller method
		     * @param selectedContentType   the content type selected through content negotiation
		     * @param selectedConverterType the converter type selected to write to the response
		     * @param request               the current request
		     * @param response              the current response
		     * @return the body that was passed in or a modified (possibly new) instance
		     */
		    @Override
		    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		        if(canPack(selectedContentType)){
		        	WithoutPack unpack = returnType.getMethod().getAnnotation(WithoutPack.class);
		        	if(unpack==null) {		        		
		        		ServletServerHttpResponse servletServerHttpResponse = (ServletServerHttpResponse) response;
		        		ResultPackBody<Object> resultPackBody = new ResultPackBody<>();
		        		resultPackBody.setData(body);
		        		resultPackBody.setStatus(servletServerHttpResponse.getServletResponse().getStatus());
		        		return resultPackBody;
		        	}
		        }
		        return body;
		    }

		    @ExceptionHandler(BaseErrorException.class)
		    @ResponseBody
		    public ResultPackBody<Object> handleException(RuntimeException e,HttpServletResponse response,HandlerMethod m){
		        ResultPackBody<Object> resultPackBody = new ResultPackBody<>();
		        WithoutPack unpack = m.getMethodAnnotation(WithoutPack.class);
		        if(e instanceof BaseErrorException) {
		        	resultPackBody.setStatus(response.getStatus());
		        	resultPackBody.setErr_code(((BaseErrorException) e).getErrorCode());
		        }
		        resultPackBody.setMessage(e.getMessage());
		        return resultPackBody;
		    }

		    /**
		     *
		     * @param mediaType 内容类型
		     * @return 是否可以构建pack对象
		     */
		    private boolean canPack(MediaType mediaType){
		        for(MediaType mType : supportTypes){
		            if(mType.includes(mediaType)){
		                return true;
		            }
		        }
		        return false;
		    }
	}
}
