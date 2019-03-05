package com.xiaour.spring.boot.config;

/**
 * 常量
 * @author ScienJus
 * @date 2015/7/31.
 */
public class Constants {

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;
    
    /**
                * 验证码有效期（分钟）
     */
    public static final int CODE_EXPIRES_MINU = 5;


    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";
    
    //极光短信MainKey
    public static final String MAINKEY = "327ea8537faa568aec04cb29";
    
  //极光短信AppKey
    public static final String APPKEY = "a492e8d9e2ab14f4c91eb752";

}
