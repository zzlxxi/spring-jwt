package com.w2site.springjwt.controller;

import com.w2site.springjwt.annotation.UserLoginToken;
import com.w2site.springjwt.domain.User;
import com.w2site.springjwt.service.TokenService;
import com.w2site.springjwt.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lizhengguang
 */
@RestController
@RequestMapping("api")
public class UserApi {

  @Autowired private UserService userService;
  @Autowired private TokenService tokenService;

  @PostMapping("/login")
  public Object login(@RequestBody User user) {
    Map<String, Object> result = new HashMap<>();
    User userForBase = userService.findByUsername(user);
    if (userForBase == null) {
      return null;
    } else {
      if (!userForBase.getPassword().equals(user.getPassword())) {
        result.put("message", "登录失败，用户不存在");
      } else {
        String token = tokenService.getToken(userForBase);
        result.put("token", token);
        result.put("user", userForBase);
      }
    }
    return result;
  }

  @UserLoginToken
  @GetMapping("/getMessage")
  public String getMessage() {
    return "已通过验证";
  }
}
