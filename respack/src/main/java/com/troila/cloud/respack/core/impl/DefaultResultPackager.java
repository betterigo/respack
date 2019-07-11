package com.troila.cloud.respack.core.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.troila.cloud.respack.core.PackEntity;
import com.troila.cloud.respack.core.RespAttrs;
import com.troila.cloud.respack.core.ResultPackager;
import com.troila.cloud.respack.core.StandardPackEntity;

/**
 * 默认的返回值结果处理工具
 * 该工具将把结果封装为3个字段 1-status; 2-err_code; 3-data
 * 如果在spring容器中配置新的ResultPackager bean，该默认值将被替换
 * @author haodonglei
 *
 */
public class DefaultResultPackager implements ResultPackager<JsonNode>{

	@Override
	public PackEntity pack(RespAttrs attrs, JsonNode data) {
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
