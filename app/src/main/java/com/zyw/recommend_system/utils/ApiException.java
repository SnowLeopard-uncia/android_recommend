package com.zyw.recommend_system.utils;

/**
 * 状态码错误的异常处理
 */
public class ApiException extends RuntimeException{
    private int errorCode;
    private String msg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ApiException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        msg = errorMessage;
    }
    /**
     * 判断是否500
     * 还有其他状态码要跟后端商量
     */
    public boolean isFail() {
        return errorCode == StatusCode.ERROR.code;
    }


    public boolean isAccessErrorReg(){
        return errorCode == StatusCode.ACCESS_ERROR.code;
    }

}
