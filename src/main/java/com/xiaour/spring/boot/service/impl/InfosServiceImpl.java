package com.xiaour.spring.boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaour.spring.boot.entity.Infos;
import com.xiaour.spring.boot.mapper.InfosMapper;
import com.xiaour.spring.boot.service.InfosServicee;

@Service
public class InfosServiceImpl implements InfosServicee {
	
	@Autowired
	private InfosMapper mapper;

	@Override
	public List<Infos> getListByType(String type) {
		return mapper.selectByType(type);
	}

	@Override
	public Infos getDetail(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

}
