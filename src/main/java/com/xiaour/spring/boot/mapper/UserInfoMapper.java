package com.xiaour.spring.boot.mapper;

import org.apache.ibatis.annotations.Param;

import com.xiaour.spring.boot.entity.UserInfo;

public interface UserInfoMapper {

    UserInfo selectByPrimaryKey(Integer id);

	UserInfo selectByPhone(@Param("phone") String phone);

	void updatePassWord(@Param("phone")String phone, @Param("passWord")String passWord);

}