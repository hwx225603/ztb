package com.xiaour.spring.boot.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiaour.spring.boot.config.Constants;
import com.xiaour.spring.boot.config.ResultStatus;
import com.xiaour.spring.boot.config.TokenModel;
import com.xiaour.spring.boot.service.TokenService;



@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
		
	@Autowired
	private TokenService tokenService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        //验证token
        TokenModel model = tokenService.getToken(authorization);
        if (tokenService.checkToken(model)) {
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(Constants.CURRENT_USER_ID, model.getUserId());
            return true;
        }else {
        	response.setStatus(ResultStatus.USER_NOT_LOGIN.getCode());
        	return false;
        }
	}

}
