package com.troila.cloud.respack.assertor;

public enum CommonEnum implements ServiceErrorEnum {
    /**
     * 表头不匹配
     */
    ERROR_EXCEL(9999, "导入excel文件不匹配"),
    ;

    private CommonEnum(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    private int errorCode;
    private String message;

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
