package com.chatop.rental.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
@Service
public class JwtService {


  public String generateToken(String email) {
    return Jwts.builder()
      .setSubject(email)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + SecParams.EXP_Time))
      .signWith(SignatureAlgorithm.HS256, SecParams.SECRET)
      .compact();
  }

  public String extractEmail(String token) {
    return Jwts.parser().setSigningKey(SecParams.SECRET).parseClaimsJws(token).getBody().getSubject();
  }
}
