package com.SeeTohJJ.Backend.auth.service;

import com.SeeTohJJ.Backend.auth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public interface JwtService {

    String generateToken(User user);
    String extractEmail(String token);
    String extractToken(String authHeader);
    Claims extractClaims(String token);
    Long extractUserId(String token);
    String extractRole(String token);
}
