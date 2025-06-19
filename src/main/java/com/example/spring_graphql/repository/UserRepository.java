package com.example.spring_graphql.repository;

import com.example.spring_graphql.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/19/2025 10:38 AM
 * @Author: huan.dovan
 */
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

}
