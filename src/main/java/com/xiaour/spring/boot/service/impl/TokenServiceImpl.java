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
	public boolean checkToken(TokenModel model) throws Exception {
		 if (model == null) {
	            return false;
	        }
	        String token = redis.get(String.valueOf(model.getUserId()));
	        if (token == null || !token.equals(model.getToken())) {
	            return false;
	        }
	        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
	        redis.expire(String.valueOf(model.getUserId()), Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
	        return true;
	}

	@Override
	public TokenModel getToken(String authentication) {
		if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        long userId = Long.parseLong(param[0]);
        String token = param[1];
        return new TokenModel(userId, token);
	}

	@Override
	public void deleteToken(long userId) {
		try {
			redis.del(String.valueOf(userId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
