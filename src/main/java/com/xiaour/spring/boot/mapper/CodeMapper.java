package com.xiaour.spring.boot.mapper;

import com.xiaour.spring.boot.entity.Code;

public interface CodeMapper {
    int deleteByPrimaryKey(String code);

    int insert(Code record);

    int insertSelective(Code record);

    Code selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(Code record);

    int updateByPrimaryKey(Code record);
}