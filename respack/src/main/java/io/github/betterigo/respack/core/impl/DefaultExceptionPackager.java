package io.github.betterigo.respack.core.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

import io.github.betterigo.respack.core.ExceptionPackger;
import io.github.betterigo.respack.core.ResultPackBody;
import io.github.betterigo.respack.exception.BaseErrorException;

/**
 * @Description 默认异常处理 
 * @author haodonglei
 * @date 2022年2月18日 上午10:54:26     
 * @Copyright 2022 Inc. All rights reserved.
 */
public class DefaultExceptionPackager implements ExceptionPackger<ResultPackBody<?>> {

	@Override
	public ResultPackBody<?> packException(BaseErrorException be,HttpServletRequest request,HttpServletResponse response,HandlerMethod m) {
        ResultPackBody<Object> resultPackBody = new ResultPackBody<>();
    	resultPackBody.setStatus(response.getStatus());
    	resultPackBody.setErr_code(be.getErrorCode());
        resultPackBody.setMessage(be.getMessage());
        return resultPackBody;
	}

}
