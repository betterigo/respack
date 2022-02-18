package io.github.betterigo.respack.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import io.github.betterigo.respack.config.settings.PackSettings;
import io.github.betterigo.respack.core.ExceptionPackger;
import io.github.betterigo.respack.core.ResultPackager;
import io.github.betterigo.respack.core.impl.DefaultExceptionPackager;
import io.github.betterigo.respack.core.impl.DefaultResultPackager;
import io.github.betterigo.respack.exception.BaseErrorException;

@Configuration
@ConditionalOnProperty(name = "respack.filter.settings.enable", havingValue = "true", matchIfMissing = true)
public class RespackWrapperAutoConfiguration {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@ConditionalOnMissingBean(ResultPackager.class)
	@Bean
	public ResultPackager<?> createResultPackager(PackSettings settings) {
		log.info("Init ResultPackager [{}]",DefaultResultPackager.class.getSimpleName());
		return new DefaultResultPackager(settings);
	}
	
	@ConditionalOnMissingBean(ExceptionPackger.class)
	@Bean
	public ExceptionPackger<?> createExceptionPackger(){
		log.info("Init ExceptionPackger [{}]",DefaultExceptionPackager.class.getSimpleName());
		return new DefaultExceptionPackager();
	}
	/**
	 * @Description 结果集装饰器
	 * @author haodonglei
	 * @date 2022年2月16日 下午3:59:20     
	 * @Copyright 2022 Inc. All rights reserved.
	 */
	@ControllerAdvice(annotations = RestController.class)
	static class ResultPackWrapper implements ResponseBodyAdvice<Object>{

		  	private ExceptionPackger<?> exceptionPackger;
		  	
		  	private ResultPackager<?> resultPackager;
		  	
		    public ResultPackWrapper(ExceptionPackger<?> exceptionPackger, ResultPackager<?> resultPackager) {
				super();
				this.exceptionPackger = exceptionPackger;
				this.resultPackager = resultPackager;
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
		        return resultPackager.packResult(body, returnType, selectedContentType, selectedConverterType, request, response);
		    }

		    @ExceptionHandler(BaseErrorException.class)
		    @ResponseBody
		    public Object handleException(RuntimeException e,HttpServletRequest request,HttpServletResponse response,HandlerMethod m){
		        return exceptionPackger.packException((BaseErrorException)e, request, response, m);
		    }
	}
}
