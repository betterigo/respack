package com.troila.cloud.respack.filter.converter.packconverter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.google.protobuf.Message;
import com.troila.cloud.respack.core.PackEntity;
import com.troila.cloud.respack.core.RespAttrs;
import com.troila.cloud.respack.core.ResultPackager;
import com.troila.cloud.respack.filter.converter.AbstractResultPackConverter;

public class ProtoResultPackConverter extends AbstractResultPackConverter<byte[]>{

	//定义默认编码类型
	public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	
	public ProtoResultPackConverter() {
		super();
		this.initSupportMediaTypes();
	}

	
	public ProtoResultPackConverter(ResultPackager<byte[]> resultPackager) {
		super(resultPackager);
		this.initSupportMediaTypes();
	}
	private void initSupportMediaTypes() {
		this.setSupportMediaTypes("application/x-protobuf");
	}
	@Override
	protected byte[] packInternal(byte[] buffer, RespAttrs respAttrs) {
		
		PackEntity entity = getResultPackager().pack(respAttrs, buffer);
		Message message = (Message) entity.getData();
		return message.toByteArray();
	}

}
