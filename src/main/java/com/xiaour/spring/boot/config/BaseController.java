package com.xiaour.spring.boot.config;

import com.xiaour.spring.boot.entity.UserInfo;

public class BaseController {
	
    public ResultModel ok(Object content) {
        return new ResultModel(ResultStatus.SUCCESS, content);
    }

    public ResultModel ok() {
        return new ResultModel(ResultStatus.SUCCESS);
    }

    public ResultModel error(ResultStatus error) {
        return new ResultModel(error);
    }
    public ResultModel error(String msg) {
        return new ResultModel(-1000,msg);
    }

}
