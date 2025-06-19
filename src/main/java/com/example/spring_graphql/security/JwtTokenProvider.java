package com.example.spring_graphql.security;

import com.example.spring_graphql.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: JwtTokenProvider cho ứng dụng Spring GraphQL.
 * @Project: spring-graphql
 * @Date: 6/19/2025 10:40 AM
 * @Author: huan.dovan
 */
@Component
public class JwtTokenProvider {

  // Khóa bí mật từ cấu hình, cần là một chuỗi Base64 dài và mạnh
  @Value("${jwt.secret}")
  private String secretKeyString; // Đổi tên để phân biệt với Key object

  @Value("${jwt.expiration-ms}")
  private long expirationMs;

  private SecretKey signingKey; // Đây sẽ là đối tượng Key thực tế để ký và xác minh

  @PostConstruct
  public void init() {
    // Khởi tạo Key từ chuỗi Base64 secretKeyString
    // Decoders.BASE64.decode(secretKeyString) sẽ chuyển đổi chuỗi Base64 thành byte array
    // Keys.hmacShaKeyFor(byte[]) sẽ tạo ra một Key an toàn từ byte array đó
    this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString));
  }

  public String generateToken(String email, Role role) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expirationMs);
    return Jwts.builder()
        .subject(email) // <-- Đã đổi từ setSubject sang subject()
        .claim("role", role.getRoleName())
        .issuedAt(now) // <-- Đã đổi từ setIssuedAt sang issuedAt()
        .expiration(expiryDate) // <-- Đã đổi từ setExpiration sang expiration()
        .signWith(signingKey)
        .compact();
  }

  // Phương thức chung để trích xuất một claim cụ thể từ token
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(signingKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();

  }

  public String getEmailFromToken(String token) {
    return extractClaim(token, Claims::getSubject); // <-- Tái sử dụng extractClaim
  }

  public String getRoleFromToken(String token) {
    return extractClaim(token,
        claims -> (String) claims.get("role")); // <-- Tái sử dụng extractClaim
  }

  public boolean validateToken(String token) {
    try {
      // Sử dụng parserBuilder và Key đã khởi tạo
      Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      // Log lỗi để dễ debug hơn
      System.err.println("JWT Validation Error: " + e.getMessage());
      return false;
    }
  }
}