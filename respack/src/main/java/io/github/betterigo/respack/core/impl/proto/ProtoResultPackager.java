package io.github.betterigo.respack.core.impl.proto;

import com.google.protobuf.ByteString;
import io.github.betterigo.respack.core.PackEntity;
import io.github.betterigo.respack.core.RespAttrs;
import io.github.betterigo.respack.core.ResultPackager;
import io.github.betterigo.respack.core.StandardPackEntity;
import io.github.betterigo.respack.core.impl.DefaultRespAttrs;

public class ProtoResultPackager implements ResultPackager<byte[],PackEntity>{

	@Override
	public PackEntity pack(RespAttrs attrs, byte[] data) {
		RespackProto.Respack.Builder builder = RespackProto.Respack.newBuilder();
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
