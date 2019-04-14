package com.troila.cloud.respack.filter.converter.packconverter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.troila.cloud.respack.core.PackEntity;
import com.troila.cloud.respack.core.RespAttrs;
import com.troila.cloud.respack.core.ResultPackager;
import com.troila.cloud.respack.filter.converter.AbstractResultPackConverter;

public class JsonResultPackConverter extends AbstractResultPackConverter<JsonNode> {
	
	//定义默认编码类型
	public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	
	ObjectMapper mapper = new ObjectMapper();
	
	public JsonResultPackConverter() {
		super();
		this.initSupportMediaTypes();
	}

	
	public JsonResultPackConverter(ResultPackager<JsonNode> resultPackager) {
		super(resultPackager);
		this.initSupportMediaTypes();
	}

	private void initSupportMediaTypes() {
		this.setSupportMediaTypes("application/json","application/x-protobuf-json");
	}

	@Override
	protected byte[] packInternal(byte[] buffer, RespAttrs respAttrs) {
		JsonNode root = null;
		try {
			String result = new String(buffer, "utf-8");
			root = mapper.readTree(result);
			PackEntity entity = getResultPackager().pack(respAttrs, root);
			return mapper.writeValueAsBytes(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
