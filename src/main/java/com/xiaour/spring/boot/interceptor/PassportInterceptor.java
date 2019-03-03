package com.xiaour.spring.boot.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sun.tools.example.debug.expr.TokenMgrError;
import com.xiaour.spring.boot.config.TokenModel;
import com.xiaour.spring.boot.service.RedisService;
import com.xiaour.spring.boot.service.TokenService;



@Component
public class PassportInterceptor extends HandlerInterceptorAdapter {
		
	@Autowired
	private TokenService tokenService;

	    public boolean preHandle(HttpServletRequest request,
	                             HttpServletResponse response, Object handler) throws Exception {
	    	
	      TokenModel model = tokenService.createToken(1);
	      System.out.println(model.getUserId());
	      return true;
	    }

}
