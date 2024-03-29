package io.github.betterigo.respack.core.impl;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.betterigo.respack.core.PackEntity;
import io.github.betterigo.respack.core.RespAttrs;
import io.github.betterigo.respack.core.ResultPackager;
import io.github.betterigo.respack.core.StandardPackEntity;

/**
 * 默认的返回值结果处理工具
 * 该工具将把结果封装为3个字段 1-status; 2-err_code; 3-data
 * 如果在spring容器中配置新的ResultPackager bean，该默认值将被替换
 * @author haodonglei
 *
 */
@Deprecated(since = "2021年2月22日，已经不使用jackson进行结果集包装了。该方式在大数据量的时候会有性能问题")
public class DefaultResultPackager implements ResultPackager<JsonNode, PackEntity>{

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
