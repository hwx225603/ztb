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
	public List<Infos> getListByType(String type,Integer pageNo,Integer pageSize) {
		if(null == pageNo || pageNo <= 0) {
			pageNo = 1;
		}
		if(null == pageSize || pageSize <= 0) {
			pageSize = 10;
		}
		//从第几条数据开始
		Integer first = (pageNo - 1) * pageSize;
        //到第几条数据结束
		Integer last = pageNo * pageSize;
		if(type.equals("0")) {
			return mapper.selectAll(first,last);
		}else {
			return mapper.selectByType(type,first,last);
		}
	}

	@Override
	public Infos getDetail(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(String ids) {
		if(null != ids) {
			String [] idss = ids.split(",");
			if(null != idss && idss.length > 0) {
				for(String id : idss) {
					mapper.deleteByPrimaryKey(Integer.parseInt(id));
				}
			}
		}
	}
}
