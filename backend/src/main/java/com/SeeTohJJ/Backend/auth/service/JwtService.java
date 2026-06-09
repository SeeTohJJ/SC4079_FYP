package com.SeeTohJJ.Backend.auth.service;

import com.SeeTohJJ.Backend.auth.model.User;

public interface JwtService {

    String generateToken(User user);
    String extractEmail(String token);
}
