package com.troila.cloud.respack.core;

import javax.servlet.http.HttpServletResponse;

import com.troila.cloud.respack.exception.BaseErrorException;

public interface AttrsSelector {
	<T extends RespAttrs> T selectResponseAtts(HttpServletResponse response);
	
	<T extends RespAttrs> T selectExceptionAtts(HttpServletResponse response,BaseErrorException e);
}
