package com.troila.cloud.respack.core.impl.proto;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.troila.cloud.respack.core.PackEntity;
import com.troila.cloud.respack.core.RespAttrs;
import com.troila.cloud.respack.core.ResultPackager;
import com.troila.cloud.respack.core.StandardPackEntity;
import com.troila.cloud.respack.core.impl.DefaultRespAttrs;

public class ProtoResultPackager implements ResultPackager<Message>{

	@Override
	public PackEntity pack(RespAttrs attrs, Message data) {
		Respack.Builder builder = Respack.newBuilder();
		builder.setStatus(attrs.getStatus());
		if(attrs instanceof DefaultRespAttrs) {
			builder.setErrCode(((DefaultRespAttrs) attrs).getErrCode());
		}
		Any.Builder anyBuilder = Any.newBuilder();
		anyBuilder.setValue(data.toByteString());
		anyBuilder.setTypeUrl(attrs.getExtInfo("X-Protobuf-Message"));
		builder.setData(anyBuilder.build());
		StandardPackEntity entity = new StandardPackEntity();
		entity.setData(builder.build());
		return entity;
	}
}
