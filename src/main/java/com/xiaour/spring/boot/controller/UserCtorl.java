package com.xiaour.spring.boot.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xiaour.spring.boot.config.BaseController;
import com.xiaour.spring.boot.config.Constants;
import com.xiaour.spring.boot.config.CurrentUser;
import com.xiaour.spring.boot.config.ResultModel;
import com.xiaour.spring.boot.config.ResultStatus;
import com.xiaour.spring.boot.config.TokenModel;
import com.xiaour.spring.boot.config.VerifyEnum;
import com.xiaour.spring.boot.entity.Infos;
import com.xiaour.spring.boot.entity.SmsCode;
import com.xiaour.spring.boot.entity.UserInfo;
import com.xiaour.spring.boot.mapper.InfosMapper;
import com.xiaour.spring.boot.mapper.SmsCodeMapper;
import com.xiaour.spring.boot.mapper.UserInfoMapper;
import com.xiaour.spring.boot.service.TokenService;
import com.xiaour.spring.boot.service.UserInfoService;
import com.xiaour.spring.boot.utils.DateUtil;
import com.xiaour.spring.boot.vo.req.InfosReq;
import com.xiaour.spring.boot.vo.req.UserVerifyReq;

import cn.jiguang.common.utils.StringUtils;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import cn.jsms.api.common.model.SMSPayload.Builder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import springfox.documentation.annotations.ApiIgnore;


/**
 * Created by huwei on 2017/4/19.
 */
@RestController
@RequestMapping(value="/user")
@Api(description="用户管理")
public class UserCtorl extends BaseController{
	
	@Autowired  
    private UserInfoMapper userInfoMapper;  
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private SmsCodeMapper smsCodeMapper;
	
	@Autowired
	private InfosMapper mapper;

    /**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	@ApiOperation(value="登录")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "phone", value = "手机", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String" ,paramType = "query")
    })
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public ResultModel login(String phone,String password) {
		if(StringUtils.isEmpty(phone)) {
			return error(ResultStatus.PARAMS_NULL);
		}
		UserInfo user = userInfoMapper.selectByPhone(phone);
		if (user == null || // 未注册
				!user.getPassWord().equals(password)) { // 密码错误
			// 提示用户名或密码错误
			return error(ResultStatus.USERNAME_OR_PASSWORD_ERROR);
		}
		// 生成一个token，保存用户登录状态
		TokenModel model = tokenService.createToken(user.getId());
		return ok(model);
	}
	/**
	 * 注册
	 * @param phone
	 * @param code
	 * @param passWord
	 * @return
	 */
	@ApiOperation(value="注册")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "phone", value = "手机", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String" ,paramType = "query"),
		@ApiImplicitParam(name = "passWord", value = "密码", required = true, dataType = "String" ,paramType = "query")
    })
	@RequestMapping(value="/reg",method = RequestMethod.POST)
	public ResultModel register(String phone,String code,String passWord) {
		//校验验证码
		try {
			List<SmsCode> smss = smsCodeMapper.selectByPhone(phone);
			if(null == smss || smss.size() == 0) {
				return error("请重新发送验证码");	
			}else if(!code.equals(smss.get(0).getCode())){
				return error("验证码错误");	
			}else{
				//判断是否注册
				UserInfo user = userInfoMapper.selectByPhone(phone);
				if(null != user) {
					return error(ResultStatus.USER_HAS_REG);	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//插入数据库
		UserInfo user = new UserInfo();
		user.setPhone(phone);
		user.setPassWord(passWord);
		user.setStatus("1");
		userInfoMapper.insert(user);
		return ok();
	}
  
	@ApiOperation(value="发短信")
	@ApiImplicitParam(name = "phone", value = "手机", required = true, dataType = "String",paramType = "query")
	@RequestMapping(value="/code",method = RequestMethod.GET)
	public ResultModel getCode(String phone) throws Exception{
		String verifyCode = String
				.valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
		//发送短信
		SMSClient sms = new SMSClient(Constants.MAINKEY,Constants.APPKEY);
		Map<String,String> params = new HashMap<String,String>();
		params.put("code", verifyCode);
		Builder b = SMSPayload.newBuilder();
		b.setMobileNumber(phone);
		b.setTempId(1);//模板ID
		b.setTempPara(params);
		SendSMSResult result = sms.sendTemplateSMS(b.build());
		if(200 == result.getResponseCode()) {
			//发送成功 存入数据库 5分钟
			List<SmsCode> smss = smsCodeMapper.selectByPhone(phone);
			if(null != smss && smss.size() > 0) {
				//更新
				SmsCode smsCode = smss.get(0);
				smsCode.setCode(verifyCode);
				smsCode.setEndTime(DateUtil.addDateMinut(Constants.CODE_EXPIRES_MINU));
				smsCodeMapper.updateByPrimaryKeySelective(smsCode);
			}else {
				//更新
				SmsCode smsCode = new SmsCode();
				smsCode.setPhone(phone);
				smsCode.setCode(verifyCode);
				smsCode.setEndTime(DateUtil.addDateMinut(Constants.CODE_EXPIRES_MINU));
				smsCodeMapper.insert(smsCode);
			}
		}else{
			return error("短信发送失败，请重试");
		}
		return ok();
	}
	
	@ApiOperation(value="修改密码")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "oldPw", value = "原始密码", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "newPw", value = "新密码", required = true, dataType = "String" ,paramType = "query"),
		@ApiImplicitParam(name = "newPw2", value = "再次新密码", required = true, dataType = "String" ,paramType = "query")
    })
	@RequestMapping(value="/modifyPass",method = RequestMethod.POST)
	public ResultModel modifyPassWord(@ApiIgnore @CurrentUser UserInfo userInfo,String oldPw,String newPw,String newPw2) throws Exception{
		if(newPw.equals(newPw2)) {
			String phone = userInfo.getPhone();
			UserInfo user = userInfoService.findByPhone(phone);
			if(null == user) {
				return error("请重新登录");
			}else {
				if(user.getPassWord().equals(oldPw)) {
					userInfoService.updatePassWord(phone,newPw);
				}else {
					return error("旧密码错误");
				}
			}
		}else {
			return error("两次密码不一致");
		}
		return ok();
	}
	
	
	@ApiOperation(value="重置密码")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String" ,paramType = "query"),
		@ApiImplicitParam(name = "newPw", value = "新密码", required = true, dataType = "String" ,paramType = "query")
    })
	@RequestMapping(value="/reSetPass",method = RequestMethod.POST)
	public ResultModel reSetPassWord(String phone,String code,String newPw) throws Exception{
		try {
			List<SmsCode> smss = smsCodeMapper.selectByPhone(phone);
			if(null == smss || smss.size() == 0) {
				return error("请重新发送验证码");	
			}else if(!code.equals(smss.get(0).getCode())){
				return error("验证码错误");	
			}else{
				//判断是否注册
				UserInfo user = userInfoMapper.selectByPhone(phone);
				if(null == user) {
					return error(ResultStatus.USER_NO_REG);	
				}else {
					userInfoMapper.updatePassWord(phone,newPw);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok();
	}
	
	@ApiOperation(value="用户信息获取")
	@RequestMapping(value="/info",method = RequestMethod.GET)
	public ResultModel getUser(@ApiIgnore @CurrentUser UserInfo userInfo) throws Exception{
		return ok(userInfo);
	}
	
	@ApiOperation(value="登出")
	@RequestMapping(value="/out",method = RequestMethod.POST)
	public ResultModel out(@ApiIgnore @CurrentUser  UserInfo userInfo) throws Exception{
		tokenService.deleteToken(userInfo.getId());
		return ok(userInfo);
	}
	
	@ApiOperation(value="认证")
	@RequestMapping(value="/verify",method = RequestMethod.POST)
	public ResultModel verify(@ApiIgnore @CurrentUser  UserInfo userInfo,UserVerifyReq req) throws Exception{
		userInfoService.verify(userInfo.getId(),req);
		return ok();
	}
	
	@ApiOperation(value="发布需求")
	@PostMapping("/pub")
	public ResultModel pub(@ApiIgnore @CurrentUser UserInfo userInfo,InfosReq req) {
		if(!VerifyEnum.YES.getCode().equals(userInfo.getHasVerify())) {
			return error("请先进行用户认证");
		}
		Infos infos = new Infos();
		infos.setTitle(req.getTitle());
		infos.setContent(req.getContent());
		infos.setType(req.getType());
		infos.setPhone(userInfo.getPhone());
		if("1".equals(userInfo.getType())) {//个人
			if(null != userInfo.getName() && userInfo.getName().length() >= 2) {
				infos.setPubliser(userInfo.getName().substring(0,1)+"*");
			}else {
				infos.setPubliser(userInfo.getName());
			}
		}else {
			infos.setPubliser(userInfo.getCompName());
		}
		mapper.insertSelective(infos);
		return ok();
	}
}
