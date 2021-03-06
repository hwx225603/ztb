package com.xiaour.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xiaour.spring.boot.entity.Message;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKeyWithBLOBs(Message record);

    int updateByPrimaryKey(Message record);

	List<Message> selectNoRead(@Param("userId")Integer userId);

	List<Message> selectByUserId(@Param("userId")Integer userId);
}