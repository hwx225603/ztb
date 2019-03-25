package com.xiaour.spring.boot.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaour.spring.boot.config.BaseController;
import com.xiaour.spring.boot.config.ResultModel;
import com.xiaour.spring.boot.entity.Banner;
import com.xiaour.spring.boot.entity.Infos;
import com.xiaour.spring.boot.service.BannerService;
import com.xiaour.spring.boot.service.InfosServicee;
import com.xiaour.spring.boot.utils.DateUtil;
import com.xiaour.spring.boot.vo.resp.IndexResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

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
	@ApiImplicitParam(name = "type", value = "信息类型(0或者不传-最新动态， 1-甲方需求，2-乙方需求，3-平台担保，4-曝光台" + 
			"5-推广甲方，6-效果渠道，7-线下流量，8-市场优化，9-流量互换" + 
			"10-营销短信，11-代理加盟，)", required = false, dataType = "String",paramType = "query")
	@RequestMapping(value="/infos",method = RequestMethod.GET)
	public ResultModel getInfos(String type,Integer pageNo,Integer pageSize) {
		type = type==null?"0":type;
		List<Infos> infos = infosService.getListByType(type,pageNo,pageSize);
		List<Infos> resp = new ArrayList<Infos>();
		for(Infos info : infos) {
			info.setTime(DateUtil.getDateX(info.getCreateTime(), new Date()));
			resp.add(info);
		}
		IndexResp res = new IndexResp();
		res.setList(resp);
		res.setTotal(infosService.getTotal(type));
		return ok(res);
	}
	
	@ApiOperation(value="动态信息详情获取")
	@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "int",paramType = "query")
	@RequestMapping(value="/infoDetail",method = RequestMethod.GET)
	public ResultModel getInfoDetail(Integer id) {
		Infos info = infosService.getDetail(id);
		return ok(info);
	}
	
	@ApiOperation(value="动态信息删除")
	@ApiImplicitParam(name = "ids", value = "IDS(英文逗号分隔)", required = true, dataType = "String",paramType = "query")
	@RequestMapping(value="/del",method = RequestMethod.GET)
	public ResultModel delete(String ids) {
		infosService.delete(ids);
		return ok();
	}
	
}
