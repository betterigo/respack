package com.troila.cloud.respack.core.impl.proto;

import com.google.protobuf.ByteString;
import com.troila.cloud.respack.core.PackEntity;
import com.troila.cloud.respack.core.RespAttrs;
import com.troila.cloud.respack.core.ResultPackager;
import com.troila.cloud.respack.core.StandardPackEntity;
import com.troila.cloud.respack.core.impl.DefaultRespAttrs;
import com.troila.cloud.respack.core.impl.proto.RespackProto.Respack;

public class ProtoResultPackager implements ResultPackager<byte[],PackEntity>{

	@Override
	public PackEntity pack(RespAttrs attrs, byte[] data) {
		Respack.Builder builder = Respack.newBuilder();
		builder.setStatus(attrs.getStatus());
		if(attrs instanceof DefaultRespAttrs) {
			builder.setErrCode(((DefaultRespAttrs) attrs).getErrCode());
		}
//		Any.Builder anyBuilder = Any.newBuilder();
//		anyBuilder.setValue(data.toByteString());
		if(attrs.getExtInfo("X-Protobuf-Message")!=null) {			
			builder.setTypeUrl(attrs.getExtInfo("X-Protobuf-Message"));
		}
		builder.setData(ByteString.copyFrom(data));
		StandardPackEntity entity = new StandardPackEntity();
		entity.setData(builder.build());
		return entity;
	}
}
