package com.troila.cloud.respack.enums;

public enum CommonStatus {

	/**
	 * 鎴愬姛
	 */
	SUCCESS(0),
	/**
	 * 鏈煡閿欒
	 */
	UNKNOWN_ERROR(1),
	/**
	 * 鏈嶅姟鏆備笉鍙敤
	 */
	SERVICE_TEMPORARILY_UNAVAILABLE(2),
	/**
	 * 鏈煡鐨勬柟娉�
	 */
	UNSUPPORTED_OPENAPI_METHOD(3),
	/**
	 * 鎺ュ彛璋冪敤娆℃暟宸茶揪鍒拌瀹氱殑涓婇檺
	 */
	OPENAPI_REQUEST_LIMIT_REACHED(4),
	/**
	 * 璇锋眰鏉ヨ嚜鏈粡鎺堟潈鐨処P鍦板潃
	 */
	UNAUTHORIZED_CLIENT_IP_ADDRESS(5),
	/**
	 * 鏃犳潈闄愯闂鐢ㄦ埛鏁版嵁
	 */
	NO_PERMISSION_TO_ACCESS_USER_DATA(6),
	/**
	 * 鏉ヨ嚜璇efer鐨勮姹傛棤璁块棶鏉冮檺
	 */
	NO_PERMISSION_TO_ACCESS_DATA_FOR_THIS_REFERER(7),
	/**
	 * 璇锋眰鍙傛暟鏃犳晥
	 */
	INVALID_PARAMETER(100),
	/**
	 * api key鏃犳晥
	 */
	INVALID_API_KEY(101),
	/**
	 * 鏃犳晥绛惧悕
	 */
	INCORRECT_SIGNATURE(104),
	/**
	 * 璇锋眰鍙傛暟杩囧
	 */
	TOO_MANY_PARAMETERS(105),
	/**
	 * 鏈煡鐨勭鍚嶆柟娉�
	 */
	UNSUPPORTED_SIGNATURE_METHOD(106),
	/**
	 * timestamp鍙傛暟鏃犳晥
	 */
	INVALID_TIMESTAMP_PARAMETER(107),
	/**
	 * 鏃犳晥鐨勭敤鎴疯祫鏂欏瓧娈靛悕
	 */
	INVALID_USER_INFO_FIELD(109),
	/**
	 * 鏃犳晥鐨刟ccess token
	 */
	ACCESS_TOKEN_INVALID(110),
	/**
	 * access token杩囨湡
	 */
	ACCESS_TOKEN_EXPIRED(111),
	/**
	 * 鐢ㄦ埛涓嶅彲瑙�
	 */
	USER_NOT_VISIBLE(210),
	/**
	 * 鑾峰彇鏈巿鏉冪殑瀛楁
	 */
	UNSUPPORTED_PERMISSION(211),
	/**
	 * 娌℃湁鏉冮檺鑾峰彇鐢ㄦ埛鐨別mail
	 */
	NO_PERMISSION_TO_ACCESS_USER_EMAIL(212),
	/**
	 * 鏈煡鐨勫瓨鍌ㄦ搷浣滈敊璇�
	 */
	UNKNOWN_DATA_STORE_API_ERROR(800),
	/**
	 * 鏃犳晥鐨勬搷浣滄柟娉�
	 */
	INVALID_OPERATION(801),
	/**
	 * 鏁版嵁瀛樺偍绌洪棿宸茶秴杩囪瀹氱殑涓婇檺
	 */
	DATA_STORE_ALLOWABLE_QUATA_WAS_EXCEEDED(802),
	/**
	 * 鎸囧畾鐨勫璞′笉瀛樺湪
	 */
	SPECIFIED_OBJECT_CANNOT_BE_FOUND(803),
	/**
	 * 鎸囧畾鐨勫璞″凡瀛樺湪
	 */
	SPECIFIED_OBJECT_ALREAD_EXISTS(804),
	/**
	 * 鏁版嵁搴撴搷浣滃嚭閿�
	 */
	A_DATABASE_ERROR_OCCURRED(805),
	/**
	 * 璇锋眰鐨勫簲鐢ㄤ笉瀛樺湪
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

