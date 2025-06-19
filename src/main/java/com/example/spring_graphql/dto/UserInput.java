package com.example.spring_graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/19/2025 1:30 PM
 * @Author: huan.dovan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInput {
  private String username;
  private String email;
  private String password;
  private Long roleId;
}
