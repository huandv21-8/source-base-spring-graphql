package com.example.spring_graphql.enums;

import com.example.spring_graphql.exception.AppException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/18/2025 2:30 PM
 * @Author: huan.dovan
 */
@Getter
public enum Messages {

  ERROR_CODE_NOT_FOUND("ERR001", "Error code not found"),
  AUTHOR_NOT_FOUND("ERR002", "Author not found"),
  PASSWORD_INVALID("ERR003", "Password is invalid"),
  FORBIDDEN("ERR004", "You do not have permission to access this resource"),
  ;


  private final String code;
  private final String message;

  Messages(String code, String message) {
    this.code = code;
    this.message = message;
  }

  // Find Enum by code
  public static Messages fromCode(String code) {
    for (Messages messages : Messages.values()) {
      if (messages.getCode().equalsIgnoreCase(code)) {
        return messages;
      }
    }
    throw new AppException(ERROR_CODE_NOT_FOUND, HttpStatus.BAD_REQUEST);
  }
}
