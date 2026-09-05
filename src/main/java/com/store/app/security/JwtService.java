package com.store.app.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;

import com.store.app.entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtService {

  private final String SECRET_KEY = System.getenv("JWT_SECRET");

  //gentration of jwt token 
  public String generateToken(User user){
    return Jwts.builder()
      .subject(user.getEmail())
      .claim("role", user.getRole())
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 ))
      .signWith(getSigningKey())
      .compact() ;      
  }

  public String extractEmail(String token){
    return Jwts.parser()
      .verifyWith(getSigningKey())
      .build()
      .parseSignedClaims(token)
      .getPayload()
      .getSubject();
  }

  public boolean isTokenValid(String token, UserDetails userDetails){
    String email = extractEmail(token);
    return email.equals(userDetails.getUsername()) && !
  }

  public boolean isTokenExpired(String token){
    return Jwts.parser()
      .verifyWith(getSigningKey())
      .build()
      .parseSignedClaims(token)
      .getPayload()
      .getExpiration()
      .before(new Date());
  }

  private SecretKey getSigningKey(){
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }



} 
