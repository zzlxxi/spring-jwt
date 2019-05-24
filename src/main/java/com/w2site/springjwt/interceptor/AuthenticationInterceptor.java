package com.w2site.springjwt.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.w2site.springjwt.annotation.PassToken;
import com.w2site.springjwt.annotation.UserloginToken;
import com.w2site.springjwt.domain.User;
import com.w2site.springjwt.service.UserService;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthenticationInterceptor implements HandlerInterceptor {

  @Autowired
  private UserService userService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    String token = request.getHeader("token");
    /**
     * 如果不是方法映射到的方法直接通过
     */
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }
    /**
     * 检查是否有passtoken注解
     */
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    Method method = handlerMethod.getMethod();
    if (method.isAnnotationPresent(PassToken.class)) {
      PassToken passToken = method.getAnnotation(PassToken.class);
      if (passToken.requiared()) {
        return true;
      }
    }
    if (method.isAnnotationPresent(UserloginToken.class)) {
      UserloginToken userloginToken = method.getAnnotation(UserloginToken.class);
      if (userloginToken.required()) {
        //执行认证
        if (token == null) {
          throw new RuntimeException("无token，请重新登录");
        }
        // 获取token中的user id
        String userId = JWT.decode(token).getAudience().get(0);
        User user = userService.findByUserId(userId);
        // 获取用户信息，此处省略
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        jwtVerifier.verify(token);
        return true;
      }
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {

  }
}
