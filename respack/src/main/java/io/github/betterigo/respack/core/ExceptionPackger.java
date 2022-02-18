package io.github.betterigo.respack.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

import io.github.betterigo.respack.exception.BaseErrorException;

public interface ExceptionPackger<T> {
	
	T packException(BaseErrorException be,HttpServletRequest request,HttpServletResponse response,HandlerMethod m);
	
}
