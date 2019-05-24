package com.w2site.springjwt.service.impl;

import com.w2site.springjwt.domain.User;
import com.w2site.springjwt.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Override
  public User findByUsername(User user) {
    return User.builder().id("1").password("123").username("test").build();
  }

  @Override
  public User findByUserId(String userId) {
    return User.builder().id("1").password("123").username("test").build();
  }
}
