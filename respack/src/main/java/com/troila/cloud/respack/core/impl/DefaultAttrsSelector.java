package com.troila.cloud.respack.core.impl;

import javax.servlet.http.HttpServletResponse;

import com.troila.cloud.respack.core.AttrsSelector;
import com.troila.cloud.respack.core.RespAttrs;
import com.troila.cloud.respack.exception.BaseErrorException;

public class DefaultAttrsSelector implements AttrsSelector{

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RespAttrs> T selectResponseAtts(HttpServletResponse response) {
		
		DefaultRespAttrs attrs = new DefaultRespAttrs();
		attrs.setStatus(response.getStatus());
		attrs.setErrCode(0);
		return (T) attrs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RespAttrs> T selectExceptionAtts(HttpServletResponse response,BaseErrorException e) {
		DefaultRespAttrs attrs = new DefaultRespAttrs();
		attrs.setStatus(response.getStatus());
		attrs.setErrCode(e.getErrorCode());
		attrs.setMessage(e.getMessage());
		return (T) attrs;
	}
}
