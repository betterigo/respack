package com.troila.cloud.respack.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class StandardPackEntity extends PackEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9166718973378669791L;
	
	private Object data;
	
	private int status;
	
	private int err_code;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getErr_code() {
		return err_code;
	}
	public void setErr_code(int err_code) {
		this.err_code = err_code;
	}

	

}
