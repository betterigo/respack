package com.troila.cloud.respack.core.impl;

import java.util.concurrent.ConcurrentHashMap;

import com.troila.cloud.respack.core.RespAttrs;

public class DefaultRespAttrs implements RespAttrs{
	
	private int status;
	
	private int errCode;
	
	private String message;

	private ConcurrentHashMap<String, String> extInfo = new ConcurrentHashMap<>();
	
	@Override
	public int getStatus() {
		return status;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setExtInfo(String k,String v) {
		this.extInfo.put(k, v);
	}
	
	public String getExtInfo(String k) {
		return this.extInfo.get(k);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
