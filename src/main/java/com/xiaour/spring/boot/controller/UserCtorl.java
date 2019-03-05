package com.xiaour.spring.boot.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaour.spring.boot.config.BaseController;
import com.xiaour.spring.boot.config.Constants;
import com.xiaour.spring.boot.config.CurrentUser;
import com.xiaour.spring.boot.config.ResultModel;
import com.xiaour.spring.boot.config.ResultStatus;
import com.xiaour.spring.boot.config.TokenModel;
import com.xiaour.spring.boot.config.noNull;
import com.xiaour.spring.boot.entity.UserInfo;
import com.xiaour.spring.boot.mapper.UserInfoMapper;
import com.xiaour.spring.boot.service.RedisService;
import com.xiaour.spring.boot.service.TokenService;
import com.xiaour.spring.boot.service.UserInfoService;

import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import cn.jsms.api.common.model.SMSPayload.Builder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * Created by huwei on 2017/4/19.
 */
@RestController
@RequestMapping(value="/user")
@Api(description="用户管理")
public class UserCtorl extends BaseController{
	
	@Autowired
	private RedisService redisService;
	
	@Autowired  
    private UserInfoMapper userInfoMapper;  
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private TokenService tokenService;

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
	public ResultModel login(@NotBlank(message="用户名不能为空") String phone,@NotBlank(message="密码不能为空")String password) {

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
	public ResultModel register(@NotBlank(message="手机号不能为空") String phone,@NotBlank(message="验证码不能为空")String code,
			@NotBlank(message="密码不能为空")String passWord) {
		//校验验证码
		try {
			String codeReal = redisService.get("phone");
			if(null == codeReal) {
				return error("请重新发送验证码");	
			}else if(!codeReal.equals(code)){
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
		return ok();
	}
    /**
                * 短信发送
     * @param phone
     * @return
     */
	@noNull(str = "phone")
	@ApiOperation(value="发短信")
	@ApiImplicitParam(name = "phone", value = "手机", required = true, dataType = "String",paramType = "path")
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
			//发送成功 存入Redis 5分钟
			redisService.set(phone, verifyCode);
			redisService.expire(phone, Constants.CODE_EXPIRES_MINU, TimeUnit.MINUTES);
			
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
	public ResultModel modifyPassWord(@CurrentUser UserInfo userInfo,@NotBlank(message="原始密码不能为空") String oldPw,
			@NotBlank(message="新密码不能为空") String newPw,String newPw2) throws Exception{
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
}
