package com.troila.cloud.respack.core;

/**
 * 该枚举提供了一般通用化的错误返回码
 * @author haodonglei
 *
 */
public enum CommonStatus {

	/**
	 * 成功
	 */
	SUCCESS(0),
	/**
	 * 未知错误
	 */
	UNKNOWN_ERROR(1),
	/**
	 * 服务暂不可用
	 */
	SERVICE_TEMPORARILY_UNAVAILABLE(2),
	/**
	 * 未知的方法
	 */
	UNSUPPORTED_OPENAPI_METHOD(3),
	/**
	 * 接口调用次数已达到设定的上限
	 */
	OPENAPI_REQUEST_LIMIT_REACHED(4),
	/**
	 * 请求来自未经授权的IP地址
	 */
	UNAUTHORIZED_CLIENT_IP_ADDRESS(5),
	/**
	 * 无权限访问该用户数据
	 */
	NO_PERMISSION_TO_ACCESS_USER_DATA(6),
	/**
	 * 来自该refer的请求无访问权限
	 */
	NO_PERMISSION_TO_ACCESS_DATA_FOR_THIS_REFERER(7),
	/**
	 * 请求参数无效
	 */
	INVALID_PARAMETER(100),
	/**
	 * api key无效
	 */
	INVALID_API_KEY(101),
	/**
	 * 无效签名
	 */
	INCORRECT_SIGNATURE(104),
	/**
	 * 请求参数过多
	 */
	TOO_MANY_PARAMETERS(105),
	/**
	 * 未知的签名方法
	 */
	UNSUPPORTED_SIGNATURE_METHOD(106),
	/**
	 * timestamp参数无效
	 */
	INVALID_TIMESTAMP_PARAMETER(107),
	/**
	 * 无效的用户资料字段名
	 */
	INVALID_USER_INFO_FIELD(109),
	/**
	 * 无效的access token
	 */
	ACCESS_TOKEN_INVALID(110),
	/**
	 * access token过期
	 */
	ACCESS_TOKEN_EXPIRED(111),
	/**
	 * 用户不可见
	 */
	USER_NOT_VISIBLE(210),
	/**
	 * 获取未授权的字段
	 */
	UNSUPPORTED_PERMISSION(211),
	/**
	 * 没有权限获取用户的email
	 */
	NO_PERMISSION_TO_ACCESS_USER_EMAIL(212),
	/**
	 * 未知的存储操作错误
	 */
	UNKNOWN_DATA_STORE_API_ERROR(800),
	/**
	 * 无效的操作方法
	 */
	INVALID_OPERATION(801),
	/**
	 * 数据存储空间已超过设定的上限
	 */
	DATA_STORE_ALLOWABLE_QUATA_WAS_EXCEEDED(802),
	/**
	 * 指定的对象不存在
	 */
	SPECIFIED_OBJECT_CANNOT_BE_FOUND(803),
	/**
	 * 指定的对象已存在
	 */
	SPECIFIED_OBJECT_ALREAD_EXISTS(804),
	/**
	 * 数据库操作出错
	 */
	A_DATABASE_ERROR_OCCURRED(805),
	/**
	 * 请求的应用不存在
	 */
	NO_SUCH_APPLICATION(900)
	;
	
	
	private int value;

	public int getValue() {
		return value;
	}

	private CommonStatus(int value) {
		this.value = value;
	}

}
