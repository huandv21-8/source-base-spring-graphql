package com.example.spring_graphql.service;

import com.example.spring_graphql.dto.UserDto;
import com.example.spring_graphql.entity.User;
import com.example.spring_graphql.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/19/2025 1:38 PM
 * @Author: huan.dovan
 */
@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserDto getCurrentUser() {
    String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = userRepository.findByEmail(email).orElseThrow();
    return UserDto.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .role(user.getRole().getRoleName())
        .build();
  }

}
