package com.notesapp.backend.config.jwt.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;

public interface JwtService {
    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    Instant extractExpirationDate(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

}
