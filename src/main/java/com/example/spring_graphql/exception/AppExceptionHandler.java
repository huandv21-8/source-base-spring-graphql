package com.example.spring_graphql.exception;

import com.example.spring_graphql.enums.Messages;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import java.util.HashMap;
import java.util.Map;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/18/2025 2:28 PM
 * @Author: huan.dovan
 */
@ControllerAdvice
public class AppExceptionHandler {

  @GraphQlExceptionHandler(AppException.class)
  public final GraphQLError handleCustomException(AppException ex,
      DataFetchingEnvironment environment) {

    Map<String, Object> extensions = new HashMap<>();
    extensions.put("errorCode",
        ex.getHttpStatus().value() + "-" + ex.getHttpStatus()); // Mã lỗi tùy chỉnh của bạn

    return GraphqlErrorBuilder.newError()
        .message(ex.getMessages().getMessage()) // Thông báo lỗi chính hiển thị cho client
        .path(environment.getExecutionStepInfo()
            .getPath()) // Đường dẫn đến trường gây lỗi trong truy vấn (ví dụ: /queryName/fieldName)
        .location(environment.getField()
            .getSourceLocation()) // Vị trí (dòng/cột) trong định nghĩa schema .graphqls của bạn
        .extensions(extensions) // Dữ liệu tùy chỉnh để client sử dụng
        .build();
  }

  @GraphQlExceptionHandler(AccessDeniedException.class)
  public GraphQLError handleAccessDenied(AccessDeniedException ex,
      DataFetchingEnvironment environment) {

    String message = Messages.FORBIDDEN.getMessage();

    return GraphqlErrorBuilder.newError()
        .message(message)
        .path(environment.getExecutionStepInfo().getPath())
        .location(environment.getField().getSourceLocation())
        .build();
  }

}
