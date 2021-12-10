package io.github.betterigo.respack.filter.converter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.github.betterigo.respack.core.PackEntity;
import io.github.betterigo.respack.core.RespAttrs;
import io.github.betterigo.respack.core.ResultPackager;
import io.github.betterigo.respack.core.impl.StringResultPackager;
import io.github.betterigo.respack.exception.OverMaxCacheException;
import io.github.betterigo.respack.filter.ResponseWrapper;

public abstract class AbstractResultPackConverter<T, R> implements ResultPackConverter {
	
	private List<String> supportsMediaTypes = Lists.newArrayList();
	
	private ResultPackager<T,R> resultPackager;
	
	private StringResultPackager defaultResultPackager;
	
	ObjectMapper mapper = new ObjectMapper();
	
	public AbstractResultPackConverter(ResultPackager<T,R> resultPackager) {
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
		byte[] result = null;
		try {
			result = packInternal(wrapper.getBytes(), respAttrs);
		} catch (OverMaxCacheException e) {//如果已经超过了最大缓存大小，则直接返回
			return;
		}
		if(result==null) {
			result = defaultPackInternal(wrapper.getBytes(), respAttrs);
			response.setContentLength(result.length);
		}
		response.setContentLengthLong(result.length);
		response.getOutputStream().write(result);
	}

	protected ResultPackager<T,R> getResultPackager(){
		return this.resultPackager;
	}
	
	public StringResultPackager getDefaultResultPackager() {
		return defaultResultPackager;
	}

	public void setDefaultResultPackager(StringResultPackager defaultResultPackager) {
		this.defaultResultPackager = defaultResultPackager;
	}

	protected abstract byte[] packInternal(byte[] buffer, RespAttrs respAttrs) throws OverMaxCacheException;
	
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
