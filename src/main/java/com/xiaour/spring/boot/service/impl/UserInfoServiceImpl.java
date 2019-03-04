package com.xiaour.spring.boot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.xiaour.spring.boot.entity.UserInfo;
import com.xiaour.spring.boot.mapper.UserInfoMapper;
import com.xiaour.spring.boot.service.UserInfoService;

public class UserInfoServiceImpl implements UserInfoService{
	
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public UserInfo findById(Integer id) {
		return userInfoMapper.selectByPrimaryKey(id);
	}

}
