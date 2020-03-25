package com.troila.cloud.respack.core.impl;

import com.troila.cloud.respack.core.PackEntity;
import com.troila.cloud.respack.core.RespAttrs;
import com.troila.cloud.respack.core.ResultPackager;
import com.troila.cloud.respack.core.StandardPackEntity;

public class StringResultPackager implements ResultPackager<String>{

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
