package com.troila.cloud.respack.filter.converter;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.troila.cloud.respack.core.RespAttrs;
import com.troila.cloud.respack.filter.ResponseWrapper;

public interface ResultPackConverter {
	void packResult(ResponseWrapper wrapper,HttpServletResponse response, RespAttrs respAttrs) throws IOException;
	
	boolean canPack(String mediaType);
}
