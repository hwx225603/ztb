package com.xiaour.spring.boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaour.spring.boot.entity.Banner;
import com.xiaour.spring.boot.mapper.BannerMapper;
import com.xiaour.spring.boot.service.BannerService;

@Service
public class BannerServiceImpl implements BannerService {
	
	@Autowired
	private BannerMapper mapper;

	@Override
	public List<Banner> getBanners() {
		return mapper.seletList();
	}

}
