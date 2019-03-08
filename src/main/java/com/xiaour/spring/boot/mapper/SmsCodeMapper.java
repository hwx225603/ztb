package com.xiaour.spring.boot.mapper;

import java.util.List;

import com.xiaour.spring.boot.entity.SmsCode;

public interface SmsCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SmsCode record);

    int insertSelective(SmsCode record);

    SmsCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SmsCode record);

    int updateByPrimaryKey(SmsCode record);

	List<SmsCode> selectByPhone(String phone);
}