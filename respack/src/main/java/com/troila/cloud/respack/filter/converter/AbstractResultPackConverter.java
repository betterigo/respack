package com.troila.cloud.respack.filter.converter;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.troila.cloud.respack.core.RespAttrs;
import com.troila.cloud.respack.core.ResultPackager;
import com.troila.cloud.respack.filter.ResponseWrapper;

public abstract class AbstractResultPackConverter<T> implements ResultPackConverter {
	
	private List<String> supportsMediaTypes = Lists.newArrayList();
	
	private ResultPackager<T> resultPackager;
	
	public AbstractResultPackConverter(ResultPackager<T> resultPackager) {
		super();
		this.resultPackager = resultPackager;
	}

	public AbstractResultPackConverter() {
		super();
	}

	public void setSupportMediaTypes(String ...mediaType) {
		for(String mType : mediaType) {
			this.supportsMediaTypes.add(mType);
		}
	}

	@Override
	public boolean canPack(String mediaType) {
		return this.supportsMediaTypes.stream().anyMatch(m->{
			return mediaType!=null && m.contains(mediaType);
			});
	}
	
	@Override
	public void packResult(ResponseWrapper wrapper, HttpServletResponse response, RespAttrs respAttrs) throws IOException {
		for(String header : response.getHeaderNames()) {
			respAttrs.setExtInfo(header, response.getHeader(header));
		}
		response.getOutputStream().write(packInternal(wrapper.getBytes(), respAttrs));
	}

	protected ResultPackager<T> getResultPackager(){
		return this.resultPackager;
	}
	
	protected abstract byte[] packInternal(byte[] buffer, RespAttrs respAttrs);
}
