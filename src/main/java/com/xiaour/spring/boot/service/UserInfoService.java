package com.xiaour.spring.boot.service;

import com.xiaour.spring.boot.entity.UserInfo;

public interface UserInfoService {
	
	public UserInfo findById(Integer id);
	
	public UserInfo findByPhone(String phone);

	public void updatePassWord(String phone, String newPw);
	
}
