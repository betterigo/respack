package com.troila.cloud.respack.core.impl;

import com.troila.cloud.respack.core.RespAttrs;

public class DefaultRespAttrs implements RespAttrs{
	
	private int status;
	
	private int errCode;

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

}
