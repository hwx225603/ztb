package com.xiaour.spring.boot.config;

/**
 * 自定义请求状态码
 * @author ScienJus
 * @date 2015/7/15.
 */
public enum ResultStatus {
    SUCCESS(100, "成功"),
    USERNAME_OR_PASSWORD_ERROR(-1001, "用户名或密码错误"),
    USER_NOT_FOUND(-1002, "用户不存在"),
    USER_NOT_LOGIN(-1003, "用户未登录"),
    PARAMS_NULL(-1005, "参数异常"),
    USER_HAS_REG(-1004, "用户已注册"),
    USER_NO_REG(-1004, "用户未注册");

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
