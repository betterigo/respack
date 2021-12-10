package io.github.betterigo.respack.core;

import javax.servlet.http.HttpServletResponse;

import io.github.betterigo.respack.exception.BaseErrorException;


public interface AttrsSelector {
	<T extends RespAttrs> T selectResponseAtts(HttpServletResponse response);
	
	<T extends RespAttrs> T selectExceptionAtts(HttpServletResponse response, BaseErrorException e);
}
