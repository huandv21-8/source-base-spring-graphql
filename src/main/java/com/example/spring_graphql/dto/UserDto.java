package com.example.spring_graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/19/2025 1:29 PM
 * @Author: huan.dovan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

  private Long id;
  private String username;
  private String email;
  private String role;
}
