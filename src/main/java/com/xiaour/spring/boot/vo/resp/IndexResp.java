package com.xiaour.spring.boot.vo.resp;

import java.util.List;

import com.xiaour.spring.boot.entity.Infos;

public class IndexResp {
	
	private List<Infos> list;
	
	private Integer total;

	public List<Infos> getList() {
		return list;
	}

	public void setList(List<Infos> list) {
		this.list = list;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
