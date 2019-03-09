package com.xiaour.spring.boot.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaour.spring.boot.config.BaseController;
import com.xiaour.spring.boot.config.Constants;
import com.xiaour.spring.boot.config.CurrentUser;
import com.xiaour.spring.boot.config.ResultModel;
import com.xiaour.spring.boot.config.VerifyEnum;
import com.xiaour.spring.boot.entity.Message;
import com.xiaour.spring.boot.entity.UserInfo;
import com.xiaour.spring.boot.mapper.MessageMapper;
import com.xiaour.spring.boot.service.ApproveService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/approve")
@Api(description="审核")
public class ApproveCtrol extends BaseController{
	
	@Autowired
	private ApproveService approveService;
	
	@Autowired
	private MessageMapper messageMapper;
	
	
	@ApiOperation(value="待审核列表")
	@GetMapping("/list")
	public ResultModel getList() throws Exception{
		List<UserInfo> data = approveService.getList();
		return ok(data);
	}
	
	@ApiOperation(value="审核")
	@GetMapping("/put")
	@ApiImplicitParam(name = "verify", value = "2-通过 3-不通过", required = true, dataType = "String",paramType = "query")
	public ResultModel putReult(Integer id,String verify) throws Exception{
		approveService.putReult(id,verify);
		//审核成功或者不成功发送消息
		Message message = new Message();
		message.setTitle(Constants.TITLE);
		message.setUserId(id);
		message.setRd("0");
		message.setCreateTime(new Date());
		if(VerifyEnum.YES.getCode().equals(verify)) {
			message.setContext(Constants.VERIFY_YES);
		}else {
			message.setContext(Constants.VERIFY_NO);
		}
		messageMapper.insertSelective(message);
		return ok();
	}
	
	@ApiOperation(value="待审核详情")
	@GetMapping("/detail")
	public ResultModel getDetail(Integer id) throws Exception{
		UserInfo data = approveService.getDetail(id);
		return ok(data);
	}

}
