package io.github.betterigo.respack.filter.converter.packconverter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.betterigo.respack.core.RespAttrs;
import io.github.betterigo.respack.core.ResultPackager;
import io.github.betterigo.respack.exception.OverMaxCacheException;
import io.github.betterigo.respack.filter.converter.AbstractResultPackConverter;

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
