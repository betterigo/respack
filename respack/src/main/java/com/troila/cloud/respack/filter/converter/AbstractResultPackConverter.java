package com.troila.cloud.respack.filter.converter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.troila.cloud.respack.core.PackEntity;
import com.troila.cloud.respack.core.RespAttrs;
import com.troila.cloud.respack.core.ResultPackager;
import com.troila.cloud.respack.core.impl.StringResultPackager;
import com.troila.cloud.respack.filter.ResponseWrapper;

public abstract class AbstractResultPackConverter<T> implements ResultPackConverter {
	
	private List<String> supportsMediaTypes = Lists.newArrayList();
	
	private ResultPackager<T> resultPackager;
	
	private StringResultPackager defaultResultPackager;
	
	ObjectMapper mapper = new ObjectMapper();
	
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
			return mediaType!=null && mediaType.contains(m);
			});
	}
	
	@Override
	public void packResult(ResponseWrapper wrapper, HttpServletResponse response, RespAttrs respAttrs) throws IOException {
		for(String header : response.getHeaderNames()) {
			respAttrs.setExtInfo(header, response.getHeader(header));
		}
		byte[] result = packInternal(wrapper.getBytes(), respAttrs);
		if(result==null) {
			result = defaultPackInternal(wrapper.getBytes(), respAttrs);
			response.setContentLength(result.length);
		}
		response.getOutputStream().write(result);
	}

	protected ResultPackager<T> getResultPackager(){
		return this.resultPackager;
	}
	
	public StringResultPackager getDefaultResultPackager() {
		return defaultResultPackager;
	}

	public void setDefaultResultPackager(StringResultPackager defaultResultPackager) {
		this.defaultResultPackager = defaultResultPackager;
	}

	protected abstract byte[] packInternal(byte[] buffer, RespAttrs respAttrs);
	
	protected byte[] defaultPackInternal(byte[] buffer, RespAttrs respAttrs) {
		try {
			String result = new String(buffer, "utf-8");
			PackEntity entity = getDefaultResultPackager().pack(respAttrs, result);
			return mapper.writeValueAsBytes(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
