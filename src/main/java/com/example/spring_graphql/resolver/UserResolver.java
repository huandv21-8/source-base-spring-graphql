package com.example.spring_graphql.resolver;

import com.example.spring_graphql.dto.UserDto;
import com.example.spring_graphql.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/19/2025 1:40 PM
 * @Author: huan.dovan
 */
@Controller
@AllArgsConstructor
public class UserResolver {

  private final UserService userService;

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @QueryMapping
  public UserDto getCurrentUser() {
    return userService.getCurrentUser();
  }

}
