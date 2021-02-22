package com.troila.cloud.respack.filter.converter.packconverter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.troila.cloud.respack.core.RespAttrs;
import com.troila.cloud.respack.core.ResultPackager;
import com.troila.cloud.respack.exception.OverMaxCacheException;
import com.troila.cloud.respack.filter.converter.AbstractResultPackConverter;

public class JsonResultPackConverter extends AbstractResultPackConverter<byte[],byte[]> {
	
	//定义默认编码类型
	public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	
	ObjectMapper mapper = new ObjectMapper();
	
	public JsonResultPackConverter() {
		super();
		this.initSupportMediaTypes();
	}

	
	public JsonResultPackConverter(ResultPackager<byte[],byte[]> resultPackager) {
		super(resultPackager);
		this.initSupportMediaTypes();
	}

	private void initSupportMediaTypes() {
		this.setSupportMediaTypes("application/json","application/x-protobuf-json");
	}

	@Override
	protected byte[] packInternal(byte[] buffer, RespAttrs respAttrs) throws OverMaxCacheException {
			return getResultPackager().pack(respAttrs, buffer);
	}

}
