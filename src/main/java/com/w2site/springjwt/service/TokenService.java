package com.w2site.springjwt.service;

import com.w2site.springjwt.domain.User;

public interface TokenService {

  String getToken(User user);
}
