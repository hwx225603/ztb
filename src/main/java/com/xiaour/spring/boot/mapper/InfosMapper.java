package com.xiaour.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xiaour.spring.boot.entity.Infos;

public interface InfosMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Infos record);

    int insertSelective(Infos record);

    Infos selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Infos record);

    int updateByPrimaryKeyWithBLOBs(Infos record);

    int updateByPrimaryKey(Infos record);

	List<Infos> selectByType(@Param("type")String type, @Param("first")Integer first, @Param("last")Integer last);
}