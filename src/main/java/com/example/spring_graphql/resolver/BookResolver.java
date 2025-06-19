package com.example.spring_graphql.resolver;

import com.example.spring_graphql.entity.Author;
import com.example.spring_graphql.entity.Book;
import com.example.spring_graphql.enums.Messages;
import com.example.spring_graphql.exception.AppException;
import com.example.spring_graphql.repository.AuthorRepository;
import com.example.spring_graphql.repository.BookRepository;
import com.example.spring_graphql.service.BookService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

/**
 * @Description:
 * @Project: spring-graphql
 * @Date: 6/18/2025 1:53 PM
 * @Author: huan.dovan
 */

@Controller
@AllArgsConstructor
public class BookResolver {

  private final BookService bookService;

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;

  // --- Query Mappings ---

  @QueryMapping // Ánh xạ tới trường 'allBooks' trong kiểu Query
  public List<Book> allBooks() {
    return bookService.allBooks();
  }

  @QueryMapping
  public Optional<Book> bookById(@Argument Long id) {
    return bookService.bookById(id);
  }

  @QueryMapping
  public List<Author> allAuthors() {
    return bookService.allAuthors();
  }

  @QueryMapping
  public Optional<Author> authorById(@Argument Long id) {
    return bookService.authorById(id);
  }

  // --- Mutation Mappings ---

  @MutationMapping // Ánh xạ tới trường 'newBook' trong kiểu Mutation
  public Book newBook(@Argument String title, @Argument Long authorId) {
    return bookService.newBook(title, authorId);
  }

  @MutationMapping
  public Author newAuthor(@Argument String firstName, @Argument String lastName) {
    return bookService.newAuthor(firstName, lastName);
  }

  // --- Schema Mappings cho các trường lồng nhau ---
  // Resolver cho trường 'author' bên trong kiểu 'Book'
  // Nếu JPA Eager Fetching không được cấu hình hoặc bạn muốn tùy chỉnh cách lấy Author
  // Tuy nhiên, với @ManyToOne mặc định và lazy loading, Spring Data JPA thường
  // tự động giải quyết tốt mà không cần @SchemaMapping rõ ràng cho mối quan hệ 1-1 đơn giản.
  // Dùng @SchemaMapping khi bạn muốn tùy chỉnh logic cho một trường cụ thể của một kiểu
  // (ví dụ: trường `books` trong `Author` nếu bạn không dùng `OneToMany` trực tiếp hoặc muốn filter)

  @SchemaMapping // Ánh xạ trường 'author' của kiểu 'Book'
  public Author author(Book book) {
    // Nếu trường 'author' trong Book đã được load (vd: Eager loading hoặc đã được query trước đó),
    // bạn chỉ cần trả về nó. Nếu chưa, bạn có thể load nó ở đây.
    // Với JPA, thông thường Book.getAuthor() sẽ tự động load khi cần (lazy loading).
    return book.getAuthor();
  }

  @SchemaMapping // Ánh xạ trường 'books' của kiểu 'Author'
  public List<Book> books(Author author) {
    // Tương tự, nếu trường 'books' trong Author đã được load (vd: Eager loading hoặc đã được query trước đó),
    // bạn chỉ cần trả về nó. Nếu chưa, bạn có thể load nó ở đây.
    // Với JPA @OneToMany, author.getBooks() sẽ tự động load khi cần.
    // Đây cũng là nơi bạn có thể thêm logic lọc, sắp xếp cho danh sách sách của một tác giả.
    return author.getBooks();
  }

}
