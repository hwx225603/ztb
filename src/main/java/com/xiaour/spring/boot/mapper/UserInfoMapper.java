package com.xiaour.spring.boot.mapper;

import org.apache.ibatis.annotations.Param;

import com.xiaour.spring.boot.entity.UserInfo;

public interface UserInfoMapper {

    UserInfo selectByPrimaryKey(Integer id);

	UserInfo selectByUserName(@Param("userName") String userName);

}