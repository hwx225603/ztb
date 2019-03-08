package com.xiaour.spring.boot.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaour.spring.boot.config.Constants;
import com.xiaour.spring.boot.config.TokenModel;
import com.xiaour.spring.boot.entity.Token;
import com.xiaour.spring.boot.mapper.TokenMapper;
import com.xiaour.spring.boot.service.TokenService;
import com.xiaour.spring.boot.utils.DateUtil;

@Service
public class TokenServiceImpl implements TokenService{
	
	@Autowired
	private TokenMapper mapper;

	@Override
	public TokenModel createToken(Integer userId) {
		 //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel model = new TokenModel(userId, token);
        try {
        	Token tk = mapper.seletByUserId(model.getUserId());
        	if(null != tk) {
        		tk.setToken(token);
        		tk.setEndTime(DateUtil.addDateDay(Constants.TOKEN_EXPIRES_HOUR));
        		mapper.updateByPrimaryKeySelective(tk);	
        	}else {
        		tk = new Token();
            	tk.setUserId(userId);
            	tk.setToken(token);
            	tk.setEndTime(DateUtil.addDateDay(Constants.TOKEN_EXPIRES_HOUR));
            	mapper.insert(tk);
        	}
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
		 	Token token = mapper.seletByUserId(model.getUserId());
		 	if (token == null || !token.getToken().equals(model.getToken())
		 		|| token.getEndTime().before(new Date())) {
	            return false;
	        }
	        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
	        token.setEndTime(DateUtil.addDateMinut(Constants.TOKEN_EXPIRES_HOUR));
	        mapper.updateByPrimaryKeySelective(token);
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
        Integer userId = Integer.parseInt(param[0]);
        String token = param[1];
        return new TokenModel(userId, token);
	}

	@Override
	public void deleteToken(Integer userId) {
		try {
			mapper.deleteByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
