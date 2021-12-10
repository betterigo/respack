package io.github.betterigo.respack.filter.converter.packconverter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.google.protobuf.Message;
import io.github.betterigo.respack.core.PackEntity;
import io.github.betterigo.respack.core.RespAttrs;
import io.github.betterigo.respack.core.ResultPackager;
import io.github.betterigo.respack.filter.converter.AbstractResultPackConverter;

public class ProtoResultPackConverter extends AbstractResultPackConverter<byte[],PackEntity>{

	//定义默认编码类型
	public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	
	public ProtoResultPackConverter() {
		super();
		this.initSupportMediaTypes();
	}

	
	public ProtoResultPackConverter(ResultPackager<byte[],PackEntity> resultPackager) {
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
