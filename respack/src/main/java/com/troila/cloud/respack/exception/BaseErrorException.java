package com.troila.cloud.respack.exception;

import com.troila.cloud.respack.assertor.ServiceErrorEnum;
import com.troila.cloud.respack.enums.CommonStatus;

public class BaseErrorException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4329392278881903686L;
	
	private int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public BaseErrorException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public BaseErrorException(int errorCode,String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public BaseErrorException(ServiceErrorEnum serviceErrorEnum) {
		super(serviceErrorEnum.getMessage());
		this.errorCode = serviceErrorEnum.getErrorCode();
	}
	
	public BaseErrorException(CommonStatus status, String message) {
		super(message);
		this.errorCode = status.getValue();
	}
	
	public BaseErrorException(CommonStatus status) {
		super();
		this.errorCode = status.getValue();
	}
	
	public BaseErrorException() {
		super();
	}

	public BaseErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BaseErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseErrorException(String message) {
		super(message);
	}

	public BaseErrorException(Throwable cause) {
		super(cause);
	}
	
}

