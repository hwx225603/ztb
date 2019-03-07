package com.xiaour.spring.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaour.spring.boot.config.BaseController;
import com.xiaour.spring.boot.config.CurrentUser;
import com.xiaour.spring.boot.config.ResultModel;
import com.xiaour.spring.boot.entity.UserInfo;
import com.xiaour.spring.boot.service.ApproveService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/approve")
@Api(description="审核")
public class ApproveCtrol extends BaseController{
	
	@Autowired
	private ApproveService approveService;
	
	
	@ApiOperation(value="待审核列表")
	@GetMapping("/list")
	public ResultModel getList() throws Exception{
		List<UserInfo> data = approveService.getList();
		return ok(data);
	}
	
	@ApiOperation(value="审核")
	@GetMapping("/put")
	public ResultModel putReult(Integer id,String verify) throws Exception{
		approveService.putReult(id,verify);
		return ok();
	}
	
	@ApiOperation(value="待审核详情")
	@GetMapping("/detail")
	public ResultModel getDetail(Integer id) throws Exception{
		UserInfo data = approveService.getDetail(id);
		return ok(data);
	}

}
