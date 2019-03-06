package com.xiaour.spring.boot.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.xiaour.spring.boot.config.ResultModel;
import com.xiaour.spring.boot.config.ResultStatus;
import com.xiaour.spring.boot.config.noNull;

@Component
public class NoNullInterceptor extends HandlerInterceptorAdapter{
	 @Override
	    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

	        //如果不是映射到方法直接通过
	        if (!(o instanceof HandlerMethod)) {
	            return true;
	        }
	        HandlerMethod handlerMethod = (HandlerMethod) o;
	        Method method = handlerMethod.getMethod();
	        if (method.getAnnotation(noNull.class) != null) {
	            noNull  noNullAnnotation=method.getAnnotation(noNull.class);
	            String str = noNullAnnotation.str();
	            String[] strs = str.split(",");
	            //从httpServletRequest获取注解上指定的参数
	            if(null != strs && strs.length > 0) {
	            	for(String s : strs) {
	            		Object obj = httpServletRequest.getParameter(s );
	     	            if(null == obj){
	     	            	obj = httpServletRequest.getAttribute(s );
	     	            	if(null == obj){
	     		            	httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");  
	     		                httpServletResponse.getWriter().write(JSON.toJSONString(new ResultModel(ResultStatus.PARAMS_NULL)));
	     		                return false;
	     		            }
	     	            }
	            	}
	            }
	            return true;	
	        }else{
	            return true;
	        }
	    }

	    //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	    @Override
	    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	    }

	    //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
	    @Override
	    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	    }
}
