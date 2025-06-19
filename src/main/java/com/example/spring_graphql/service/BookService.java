package com.example.spring_graphql.service;

import com.example.spring_graphql.entity.Author;
import com.example.spring_graphql.entity.Book;
import com.example.spring_graphql.enums.Messages;
import com.example.spring_graphql.exception.AppException;
import com.example.spring_graphql.repository.AuthorRepository;
import com.example.spring_graphql.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/19/2025 4:47 PM
 * @Author: huan.dovan
 */
@Service
@AllArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;


  public List<Book> allBooks() {
    return bookRepository.findAll();
  }

  public Optional<Book> bookById(Long id) {
    return bookRepository.findById(id);
  }

  public List<Author> allAuthors() {
    return authorRepository.findAll();
  }

  public Optional<Author> authorById(Long id) {
    return authorRepository.findById(id);
  }

  public Book newBook(String title, Long authorId) {
    Author author = authorRepository.findById(authorId)
        .orElseThrow(() -> new AppException(Messages.AUTHOR_NOT_FOUND, HttpStatus.BAD_REQUEST));
    Book book = new Book();
    book.setTitle(title);
    book.setAuthor(author);
    return bookRepository.save(book);
  }

  public Author newAuthor( String firstName,  String lastName) {
    Author author = new Author();
    author.setFirstName(firstName);
    author.setLastName(lastName);
    return authorRepository.save(author);
  }


}
