package com.xiaour.spring.boot.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.xiaour.spring.boot.interceptor.CurrentUserResolver;
import com.xiaour.spring.boot.interceptor.TokenInterceptor;


/**
 * 配置类，增加自定义拦截器和解析器
 * @see com.scienjus.authorization.resolvers.CurrentUserMethodArgumentResolver
 * @see com.scienjus.authorization.interceptor.AuthorizationInterceptor
 * @author ScienJus
 * @date 2015/7/30.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	String [] path = new String[] {"/**/swagger-resources/**","/**/user/code",
			"/**/user/login","/**/user/reg","/**/user/reSetPass","/**/img/upload",
			"/**/index/*","/**/shutdown/*"};

    @Autowired
    private TokenInterceptor tokenInterceptor;
    
    @Autowired
    private CurrentUserResolver currentUserResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(tokenInterceptor).excludePathPatterns(path);
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserResolver);
    }
    
}
