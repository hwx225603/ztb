package com.xiaour.spring.boot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaour.spring.boot.entity.UserInfo;
import com.xiaour.spring.boot.mapper.UserInfoMapper;
import com.xiaour.spring.boot.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public UserInfo findById(Integer id) {
		return userInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public UserInfo findByPhone(String phone) {
		return userInfoMapper.selectByPhone(phone);
	}

	@Override
	public void updatePassWord(String phone, String newPw) {
		userInfoMapper.updatePassWord(phone,newPw);
	}

}
