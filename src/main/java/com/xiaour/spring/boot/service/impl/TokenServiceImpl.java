package com.xiaour.spring.boot.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaour.spring.boot.config.Constants;
import com.xiaour.spring.boot.config.TokenModel;
import com.xiaour.spring.boot.service.RedisService;
import com.xiaour.spring.boot.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService{
	
	@Autowired
	private RedisService redis;

	@Override
	public TokenModel createToken(long userId) {
		 //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel model = new TokenModel(userId, token);
        //存储到redis并设置过期时间
        try {
        	redis.set(String.valueOf(userId), token);
			redis.expire(String.valueOf(userId), Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return model;
	}

	@Override
	public boolean checkToken(TokenModel model) {
		return false;
	}

	@Override
	public TokenModel getToken(String authentication) {
		return null;
	}

	@Override
	public void deleteToken(long userId) {
		
	}
	

}
