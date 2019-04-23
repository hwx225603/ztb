package com.xiaour.spring.boot.controller;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaour.spring.boot.config.BaseController;
import com.xiaour.spring.boot.config.ResultModel;
import com.xiaour.spring.boot.entity.Code;
import com.xiaour.spring.boot.mapper.CodeMapper;


@RestController
@RequestMapping(value="/code")
public class CodeCtrol extends BaseController{
	
	@Autowired
	private CodeMapper codeMapper;
	
	@RequestMapping(value="/get",method = RequestMethod.GET)
	public String getBanners(String code,String mac) {
		Code cd = codeMapper.selectByPrimaryKey(code);
		String i = "";
		if(null != cd) {
			if(null == cd.getMachine()) {
				cd.setMachine(mac);
				Calendar calendar = Calendar.getInstance();  
			    calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 7);  
			    Date today = calendar.getTime();  
			    cd.setTime(today);
			    codeMapper.updateByPrimaryKeySelective(cd);
				i = "1";//首次激活
			}else if(!mac.equals(cd.getMachine())){
				i = "2";//已绑定其他机器
			}else if(new Date().after(cd.getTime())){
				i = "3";//已过期
			}else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
				i = sdf.format(cd.getTime());//成功
			}
		}else {
			i = "0";//激活码错误
		}
		return i;
	}
	@RequestMapping(value="/ct",method = RequestMethod.GET)
	public String getCode() {
		Code cd = new Code();
		String r = getRandomString(8);
		cd.setCode(r);
		codeMapper.insertSelective(cd);
		return r;
	}
	
	public static String getRandomString(int length){
	     String str="0ab5cd1efgh8ijklm2nop6qrs3tuv7wx4yz9";
	     Random random=new Random();
	     StringBuffer sb=new StringBuffer();
	     for(int i=0;i<length;i++){
	       int number=random.nextInt(36);
	       sb.append(str.charAt(number));
	     }
	     return sb.toString();
	 }
	
}
