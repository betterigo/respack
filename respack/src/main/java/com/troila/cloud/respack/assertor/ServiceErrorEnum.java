package com.troila.cloud.respack.assertor;

public interface ServiceErrorEnum {
	
	int getErrorCode();
	String getMessage();
	
	static ServiceErrorEnum instance(int code,String message) {
		return new ServiceErrorEnum() {
			@Override
			public String getMessage() {
				return message;
			}
			@Override
			public int getErrorCode() {
				return code;
			}
		};
	}
}
