package com.example.spring_graphql.exception;

import com.example.spring_graphql.enums.Messages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/18/2025 2:29 PM
 * @Author: huan.dovan
 */
@AllArgsConstructor
@Getter
public class AppException extends RuntimeException {

  private final Messages messages;

  private final HttpStatus httpStatus;

}
