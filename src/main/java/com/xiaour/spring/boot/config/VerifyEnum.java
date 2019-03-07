package com.xiaour.spring.boot.config;

public enum VerifyEnum {
	
	 NO("0", "未认证"),
	 ING("1", "认证中"),
	 YES("2", "认证"),
	 ER("3", "未通过");

	
	 private String code;

    /**
     * 返回结果描述
     */
    private String message;

    VerifyEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
