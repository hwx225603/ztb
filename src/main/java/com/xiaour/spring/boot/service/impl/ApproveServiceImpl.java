package com.xiaour.spring.boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaour.spring.boot.entity.UserInfo;
import com.xiaour.spring.boot.mapper.UserInfoMapper;
import com.xiaour.spring.boot.service.ApproveService;

@Service
public class ApproveServiceImpl implements ApproveService {
	
	@Autowired
	private UserInfoMapper mapper;

	@Override
	public List<UserInfo> getList() {
		return mapper.getList();
	}

	@Override
	public void putReult(Integer id, String verify,String type) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(id);
		if("1".equals(type)) {
			userInfo.setHasVerifyP(verify);	
		}else {
			userInfo.setHasVerifyC(verify);	
		}
		mapper.updateByPrimaryKeySelective(userInfo);
	}

	@Override
	public UserInfo getDetail(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	
}
