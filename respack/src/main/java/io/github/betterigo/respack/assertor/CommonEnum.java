package io.github.betterigo.respack.assertor;

public enum CommonEnum implements ServiceErrorEnum {
    /**
     * 表头不匹配
     */
    SUCCESS(0,"成功"),
    UNKNOWN_ERROR(1,"未知错误"),
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
