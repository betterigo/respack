package io.github.betterigo.respack.core.impl;

import io.github.betterigo.respack.core.PackEntity;
import io.github.betterigo.respack.core.RespAttrs;
import io.github.betterigo.respack.core.ResultPackager;
import io.github.betterigo.respack.core.StandardPackEntity;

public class StringResultPackager implements ResultPackager<String,PackEntity>{

	@Override
	public PackEntity pack(RespAttrs attrs, String data) {
		StandardPackEntity packEntity = new StandardPackEntity();
		if(attrs instanceof DefaultRespAttrs) {
			packEntity.setErr_code(((DefaultRespAttrs) attrs).getErrCode());
			packEntity.setMsg(((DefaultRespAttrs) attrs).getMessage());
		}
		packEntity.setStatus(attrs.getStatus());
		packEntity.setData(data);
		return packEntity;
	}

}
