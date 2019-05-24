package com.w2site.springjwt.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.w2site.springjwt.domain.User;
import com.w2site.springjwt.service.TokenService;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

  @Override
  public String getToken(User user) {
    String token;
    token = JWT.create().withAudience(user.getId()).sign(Algorithm.HMAC256(user.getPassword()));
    return token;
  }
}
