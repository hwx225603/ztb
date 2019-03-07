package com.xiaour.spring.boot.service;

import com.xiaour.spring.boot.entity.UserInfo;
import com.xiaour.spring.boot.vo.req.UserVerifyReq;

public interface UserInfoService {
	
	public UserInfo findById(Integer id);
	
	public UserInfo findByPhone(String phone);

	public void updatePassWord(String phone, String newPw);

	public void verify(Integer id, UserVerifyReq req);
	
}
