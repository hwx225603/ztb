package com.xiaour.spring.boot.service;

import java.util.List;

import com.xiaour.spring.boot.entity.Infos;

public interface InfosServicee {

	List<Infos> getListByType(String type);

	Infos getDetail(Integer id);

}
