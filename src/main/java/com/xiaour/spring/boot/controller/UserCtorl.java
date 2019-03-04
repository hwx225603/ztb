package com.xiaour.spring.boot.controller;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaour.spring.boot.config.BaseController;
import com.xiaour.spring.boot.config.ResultModel;
import com.xiaour.spring.boot.config.ResultStatus;
import com.xiaour.spring.boot.config.TokenModel;
import com.xiaour.spring.boot.entity.UserInfo;
import com.xiaour.spring.boot.mapper.UserInfoMapper;
import com.xiaour.spring.boot.service.RedisService;
import com.xiaour.spring.boot.service.TokenService;
import com.xiaour.spring.boot.service.UserInfoService;
import com.xiaour.spring.boot.utils.JsonUtil;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import cn.jsms.api.common.model.SMSPayload.Builder;


/**
 * Created by huwei on 2017/4/19.
 */
@RestController
@RequestMapping(value="/user")
public class UserCtorl extends BaseController{
	
	@Autowired
	private RedisService redisService;
	
	@Autowired  
    private UserInfoMapper userInfoMapper;  
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private TokenService tokenService;

    @RequestMapping(value="/index")
    public String index(){
        return "hello world";
    }
    
    /**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public ResultModel login(@NotBlank(message="用户名不能为空") String phone,@NotBlank(message="密码不能为空")String password) {

		UserInfo user = userInfoMapper.selectByPhone(phone);
		if (user == null || // 未注册
				!user.getPassWord().equals(password)) { // 密码错误
			// 提示用户名或密码错误
			return error(ResultStatus.USER_NOT_FOUND);
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
    
	@RequestMapping(value="/code",method = RequestMethod.POST)
	public ResultModel getCode(@NotBlank(message="手机号不能为空") String phone) {
		
		return ok();
	}
	
	public static void main(String[] args) throws APIConnectionException, APIRequestException {
		SMSClient sms = new SMSClient("327ea8537faa568aec04cb29","a492e8d9e2ab14f4c91eb752");
		String verifyCode = String
				.valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
		Map<String,String> params = new HashMap<String,String>();
		System.out.println(verifyCode);
		params.put("code", verifyCode);
		Builder b = SMSPayload.newBuilder();
		b.setMobileNumber("15915448330");
		b.setTempId(1);
		b.setTempPara(params);
		SendSMSResult result = sms.sendTemplateSMS(b.build());
		System.out.println(result.getMessageId());
	}
}
