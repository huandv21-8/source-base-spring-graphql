package com.example.spring_graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/19/2025 1:31 PM
 * @Author: huan.dovan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthPayload {

  private String token;
  private UserDto user;
}