package io.github.betterigo.respack.core.impl;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.primitives.Bytes;
import io.github.betterigo.respack.core.RespAttrs;
import io.github.betterigo.respack.core.ResultPackager;

public class ByteResultPackager implements ResultPackager<byte[], byte[]> {

	private ObjectMapper mapper = new ObjectMapper();
	
	private static final byte[] DATA_PREFIX = ",\"data\":".getBytes();
	
	private static final byte[] JSON_END = "}".getBytes();
	
	@Override
	public byte[] pack(RespAttrs attrs, byte[] data) {
		ObjectNode packNode = new ObjectNode(JsonNodeFactory.instance);
		packNode.put("status", attrs.getStatus());
		packNode.put("err_code", ((DefaultRespAttrs) attrs).getErrCode());
		String message = ((DefaultRespAttrs) attrs).getMessage();
		if(message!=null) {			
			packNode.put("msg", message);
		}
		try {
			byte[] resb = mapper.writeValueAsBytes(packNode);
			if(data!=null && data.length>0) {				
				byte[] prefixByte = Arrays.copyOfRange(resb, 0, resb.length-1);
				return Bytes.concat(prefixByte,DATA_PREFIX,data,JSON_END);
			}else {
				return resb;
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
