package com.xiaour.spring.boot.service;

import java.util.List;

import com.xiaour.spring.boot.entity.UserInfo;

public interface ApproveService {

	List<UserInfo> getList();

	void putReult(Integer id, String verify);

	UserInfo getDetail(Integer id);

}
