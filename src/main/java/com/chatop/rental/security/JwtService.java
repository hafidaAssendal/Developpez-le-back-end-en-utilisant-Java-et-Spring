package com.chatop.rental.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
  @Value("${app.secret-key}")
  private String secretKey;
  @Value("${app.exp-time}")
  private long expTime;

  public String generateToken(String email) {
    String token = Jwts.builder()
      .setSubject(email)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + expTime))
      .signWith(SignatureAlgorithm.HS256, secretKey)
      .compact();

    System.out.println("JWT généré : " + token);
    return token;
  }


  public String extractEmail(String token) {
    return Jwts.parser()
               .setSigningKey(secretKey)
               .parseClaimsJws(token)
               .getBody()
               .getSubject();
  }
}
