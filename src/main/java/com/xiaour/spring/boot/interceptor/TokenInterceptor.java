package com.xiaour.spring.boot.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.xiaour.spring.boot.config.Constants;
import com.xiaour.spring.boot.config.ResultModel;
import com.xiaour.spring.boot.config.ResultStatus;
import com.xiaour.spring.boot.config.TokenModel;
import com.xiaour.spring.boot.service.TokenService;



@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
		
	@Autowired
	private TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        authorization = "1_f213db28fc264c5b8f4a708dcf431217";
        //验证token
        TokenModel model = tokenService.getToken(authorization);
        if (tokenService.checkToken(model)) {
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(Constants.CURRENT_USER_ID, model.getUserId());
            return true;
        }else {
        	response.setHeader("Content-type", "text/html;charset=UTF-8");  
        	response.getWriter().write(JSON.toJSONString(new ResultModel(ResultStatus.USER_NOT_LOGIN)));
        	return false;
        }
	}
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
