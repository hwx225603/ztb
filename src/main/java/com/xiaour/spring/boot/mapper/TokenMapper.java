package com.xiaour.spring.boot.mapper;

import com.xiaour.spring.boot.entity.Token;

public interface TokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Token record);

    int insertSelective(Token record);

    Token selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Token record);

    int updateByPrimaryKey(Token record);

	Token seletByUserId(Integer userId);

	void deleteByUserId(Integer userId);
}