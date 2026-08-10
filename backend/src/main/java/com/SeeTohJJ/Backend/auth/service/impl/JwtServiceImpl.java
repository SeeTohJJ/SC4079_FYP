package com.SeeTohJJ.Backend.auth.service.impl;

import com.SeeTohJJ.Backend.auth.model.User;
import com.SeeTohJJ.Backend.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    @Override
    public String generateToken(User user) {

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getUserId())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }

    @Override
    public String extractEmail(String token) {

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public String extractToken(String authHeader) {
        return authHeader.substring(7); // remove "Bearer "
    }

    @Override
    public Claims extractClaims(String token) {

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public Long extractUserId(String token) {
        Claims claims = extractClaims(token);
        return Long.parseLong(claims.get("userId").toString());
    }

    @Override
    public String extractRole(String token) {

        Claims claims = extractClaims(token);

        return claims.get("role", String.class);
    }
}
