package com.example.spring_graphql.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/18/2025 1:50 PM
 * @Author: huan.dovan
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String firstName;
  private String lastName;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Book> books; // Đảm bảo trường này khớp với 'books' trong schema Author
}
