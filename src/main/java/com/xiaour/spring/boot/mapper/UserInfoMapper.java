package com.xiaour.spring.boot.mapper;

import org.apache.ibatis.annotations.Param;

import com.xiaour.spring.boot.entity.UserInfo;

public interface UserInfoMapper {

    UserInfo selectByPrimaryKey(Integer id);

	UserInfo selectByPhone(@Param("userName") String userName);

}