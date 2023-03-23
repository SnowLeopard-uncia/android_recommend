package com.zyw.recommend_system.utils;

/**
 * 封装状态码
 */
public enum StatusCode {

    //http定义好的状态码
    SUCCESS(20000),
    /**
     * 失败
     */
    ERROR(20001),
    SERVER_ERROR(500),

    URL_NOT_FOUND(404),

    /**
     * 参数校验错误
     */
    PARAM_ERROR(400),
    /**
     * 权限不足
     */
    ACCESS_ERROR(401),
    /**
     * 验证码错误
     */
    CODE_ERROR(20005),
    /**
     * 重复操作
     */
    REP_ERROR(20005),
    /**
     * 用户名或密码错误
     */
    LOGIN_ERROR(20002);


    public int code;

    StatusCode(int code)
    {
        this.code=code;
    }

}
