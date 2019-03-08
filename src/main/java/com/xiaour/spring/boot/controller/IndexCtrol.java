package com.xiaour.spring.boot.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaour.spring.boot.config.BaseController;
import com.xiaour.spring.boot.config.CurrentUser;
import com.xiaour.spring.boot.config.ResultModel;
import com.xiaour.spring.boot.config.VerifyEnum;
import com.xiaour.spring.boot.entity.Banner;
import com.xiaour.spring.boot.entity.Infos;
import com.xiaour.spring.boot.entity.UserInfo;
import com.xiaour.spring.boot.mapper.InfosMapper;
import com.xiaour.spring.boot.service.BannerService;
import com.xiaour.spring.boot.service.InfosServicee;
import com.xiaour.spring.boot.utils.DateUtil;
import com.xiaour.spring.boot.vo.req.InfosReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by huwei on 2017/4/19.
 */
@RestController
@RequestMapping(value="/index")
@Api(description="首页管理")
public class IndexCtrol extends BaseController{
	
	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private InfosServicee infosService;
	
	@ApiOperation(value="banner获取")
	@RequestMapping(value="/banner",method = RequestMethod.GET)
	public ResultModel getBanners() {
		List<Banner> resp = bannerService.getBanners();
		return ok(resp);
	}
	
	@ApiOperation(value="动态信息列表获取")
	@ApiImplicitParam(name = "type", value = "信息类型(1-最新动态，2-甲方需求，3-乙方需求，4-平台担保，5-曝光台)", required = true, dataType = "String",paramType = "query")
	@RequestMapping(value="/infos",method = RequestMethod.GET)
	public ResultModel getInfos(String type) {
		type = type==null?"1":type;
		List<Infos> infos = infosService.getListByType(type);
		List<Infos> resp = new ArrayList<Infos>();
		for(Infos info : infos) {
			info.setTime(DateUtil.getDateX(info.getCreateTime(), new Date()));
			resp.add(info);
		}
		return ok(resp);
	}
	
	@ApiOperation(value="动态信息详情获取")
	@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "int",paramType = "query")
	@RequestMapping(value="/infoDetail",method = RequestMethod.GET)
	public ResultModel getInfoDetail(Integer id) {
		Infos info = infosService.getDetail(id);
		return ok(info);
	}
	
	
}
