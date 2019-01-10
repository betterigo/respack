package com.troila.cloud.respack.exception;

/**
 * 基本异常类，包装字段中的的err_code将从该异常获得。用户可以继承该异常来自定义自己的异常信息
 * @author haodonglei
 *
 */
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
