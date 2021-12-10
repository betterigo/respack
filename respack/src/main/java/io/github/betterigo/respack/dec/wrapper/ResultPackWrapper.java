/*
 * Copyright (c) 2021. All Rights Reserved.
 * ProjectName:  demo1
 * ClassName: RespackWrapper
 * Author: hdl
 * Date: 2021/12/9 下午3:09
 */

package io.github.betterigo.respack.dec.wrapper;

import io.github.betterigo.respack.dec.base.ResultPackBody;
import io.github.betterigo.respack.exception.BaseErrorException;
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
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hdl
 * @since 2021/12/9 15:09
 */
@ControllerAdvice(annotations = RestController.class)
public class ResultPackWrapper implements ResponseBodyAdvice<Object> {

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
            ServletServerHttpResponse servletServerHttpResponse = (ServletServerHttpResponse) response;
            ResultPackBody<Object> resultPackBody = new ResultPackBody<>();
            resultPackBody.setData(body);
            resultPackBody.setStatus(servletServerHttpResponse.getServletResponse().getStatus());
//            if(body instanceof String){
//                return JSON.toJSONString(resultPackBody);
//            }
            return resultPackBody;
        }
        return body;
    }

    @ExceptionHandler(BaseErrorException.class)
    @ResponseBody
    public ResultPackBody<Object> handleException(RuntimeException e){
        ResultPackBody<Object> resultPackBody = new ResultPackBody<>();
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
