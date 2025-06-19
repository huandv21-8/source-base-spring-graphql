package com.example.spring_graphql.resolver;

import com.example.spring_graphql.dto.AuthPayload;
import com.example.spring_graphql.dto.UserInput;
import com.example.spring_graphql.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/19/2025 1:39 PM
 * @Author: huan.dovan
 */
@Controller
@AllArgsConstructor
public class AuthResolver {

  private final AuthService authService;

  @MutationMapping
  public AuthPayload register(@Argument("user") UserInput user) {
    return authService.register(user);
  }

  @MutationMapping
  public AuthPayload login(@Argument String email, @Argument String password) {
    return authService.login(email, password);
  }
}

