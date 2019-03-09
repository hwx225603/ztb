package com.xiaour.spring.boot.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaour.spring.boot.config.BaseController;
import com.xiaour.spring.boot.config.CurrentUser;
import com.xiaour.spring.boot.config.ResultModel;
import com.xiaour.spring.boot.entity.Message;
import com.xiaour.spring.boot.entity.UserInfo;
import com.xiaour.spring.boot.mapper.MessageMapper;
import com.xiaour.spring.boot.utils.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value="/msg")
@Api(description="消息")
public class MessageCtrol extends BaseController{
	
	@Autowired
	private MessageMapper mapper;
	
	@ApiOperation(value="是否有新消息")
	@GetMapping("/new")
	public ResultModel newMsg(@ApiIgnore @CurrentUser UserInfo userInfo) {
		List<Message> msgs = mapper.selectNoRead(userInfo.getId());
		Map<String,Boolean> resp = new HashMap<String, Boolean>();
		if(null != msgs && msgs.size() > 0) {
			resp.put("new",true);
		}else {
			resp.put("new",false);
		}
		return ok(resp);
	}
	
	@ApiOperation(value="消息列表")
	@GetMapping("/newsList")
	public ResultModel newsList(@ApiIgnore @CurrentUser UserInfo userInfo) {
		List<Message> msgs = mapper.selectByUserId(userInfo.getId());
		List<Message> resp = new ArrayList<Message>();
		for(Message msg : msgs) {
			msg.setTime(DateUtil.getDateX(msg.getCreateTime(), new Date()));
			resp.add(msg);
		}
		return ok(msgs);
	}
	
	@ApiOperation(value="消息详情")
	@GetMapping("/newDetail")
	public ResultModel newDetail(Integer id) {
		Message msg = mapper.selectByPrimaryKey(id);
		msg.setTime(DateUtil.getDateX(msg.getCreateTime(), new Date()));
		return ok(msg);
	}
	
	@ApiOperation(value="设置成已读")
	@PostMapping("/newDetail")
	public ResultModel putRead(Integer id) {
		Message msg = new Message();
		msg.setId(id);
		msg.setRd("1");
		mapper.updateByPrimaryKeySelective(msg);
		return ok();
	}
}
