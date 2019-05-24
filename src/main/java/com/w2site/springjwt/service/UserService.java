package com.w2site.springjwt.service;

import com.w2site.springjwt.domain.User;

public interface UserService {

  User findByUsername(User user);

  User findByUserId(String userId);
}
