package com.example.spring_graphql.service;

import com.example.spring_graphql.dto.AuthPayload;
import com.example.spring_graphql.dto.UserDto;
import com.example.spring_graphql.entity.Role;
import com.example.spring_graphql.entity.User;
import com.example.spring_graphql.exception.AppException;
import com.example.spring_graphql.repository.RoleRepository;
import com.example.spring_graphql.repository.UserRepository;
import com.example.spring_graphql.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.spring_graphql.dto.UserInput;
import com.example.spring_graphql.enums.Messages;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/19/2025 1:33 PM
 * @Author: huan.dovan
 */
@Service
@AllArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final RoleRepository roleRepository;


  public AuthPayload register(UserInput input) {
    Role role = roleRepository.getReferenceById(input.getRoleId());

    User user = User.builder()
        .username(input.getUsername())
        .email(input.getEmail())
        .password(passwordEncoder.encode(input.getPassword()))
        .role(role)
        .build();
    userRepository.save(user);
    String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole());
    return new AuthPayload(token, toDto(user));
  }

  public AuthPayload login(String email, String password) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new AppException(Messages.PASSWORD_INVALID, HttpStatus.BAD_REQUEST);
    }
    String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole());
    return new AuthPayload(token, toDto(user));
  }

  public UserDto toDto(User user) {
    return UserDto.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .role(user.getRole().getRoleName())
        .build();
  }
}

