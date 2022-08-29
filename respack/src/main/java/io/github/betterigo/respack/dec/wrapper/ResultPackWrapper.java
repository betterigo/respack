/*
 * Copyright (c) 2021. All Rights Reserved.
 * ProjectName:  demo1
 * ClassName: RespackWrapper
 * Author: hdl
 * Date: 2021/12/9 下午3:09
 */

package io.github.betterigo.respack.dec.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import io.github.betterigo.respack.config.settings.PackPatternAdapter;
import io.github.betterigo.respack.config.settings.PackPatternMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.betterigo.respack.dec.annotation.WithoutPack;
import io.github.betterigo.respack.dec.base.PatternUtil;
import io.github.betterigo.respack.dec.base.RespackResultResolver;
import io.github.betterigo.respack.exception.BaseErrorException;

/**
 * @author hdl
 * @since 2021/12/9 15:09
 */
@ControllerAdvice(annotations = RestController.class)
public class ResultPackWrapper implements ResponseBodyAdvice<Object> {

    private List<MediaType> supportTypes;

//    @Autowired
//    private FilterSettings filterSettings;
    
    @Autowired
    private RespackResultResolver respackResultResolver;

    @Autowired
    private PackPatternAdapter packPatternAdapter;

    private boolean blackMode;
    
    private List<String> ignorePaths;
    
    ObjectMapper mapper = new ObjectMapper();
    
    @PostConstruct
    void init(){
        supportTypes = new ArrayList<>();
        supportTypes.add(new MediaType("application","json"));
        supportTypes.add(new MediaType("text","plain"));
        supportTypes.add(new MediaType("application","hal+json"));
        supportTypes = Collections.unmodifiableList(supportTypes);
        ignorePaths = packPatternAdapter.getPatterns();
        this.blackMode = Objects.equals(packPatternAdapter.patternMode(), PackPatternMode.BLACK_LIST);
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
    	WithoutPack noPack = returnType.getMethodAnnotation(WithoutPack.class);
    	if(noPack!=null) {
    		return body;
    	}
    	String uri = request.getURI().getPath();
    	if (matchUri(uri)) {
    		return body;
    	}
        if(canPack(selectedContentType)){
            return respackResultResolver.pack(body, returnType, selectedContentType, request, response);
        }
        return body;
    }

    @ExceptionHandler(BaseErrorException.class)
    @ResponseBody
    public Object handleException(BaseErrorException e){
        return respackResultResolver.handleException(e);
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


	private boolean matchUri(String uri) {
		for (String uriPattern : ignorePaths) {
			if (PatternUtil.match(uriPattern, uri)) {
                return blackMode;
			}
		}
		return blackMode ? false : true;
	}
}
