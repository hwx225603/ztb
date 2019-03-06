package com.xiaour.spring.boot.mapper;

import org.apache.ibatis.annotations.Param;

import com.xiaour.spring.boot.entity.UserInfo;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    UserInfo selectByPhone(@Param("phone") String phone);

	void updatePassWord(@Param("phone")String phone, @Param("passWord")String passWord);
}