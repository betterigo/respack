package io.github.betterigo.respack.filter.converter;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import io.github.betterigo.respack.core.RespAttrs;
import io.github.betterigo.respack.filter.ResponseWrapper;

public interface ResultPackConverter {
	void packResult(ResponseWrapper wrapper,HttpServletResponse response, RespAttrs respAttrs) throws IOException;
	
	boolean canPack(String mediaType);
}
