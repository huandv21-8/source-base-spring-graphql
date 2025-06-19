package com.example.spring_graphql.repository;

import com.example.spring_graphql.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/18/2025 1:52 PM
 * @Author: huan.dovan
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}