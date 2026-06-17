package com.doconnect.security;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/** 

 * JwtUtil 

 * Utility class for JWT operations 

 * Generates, validates and parses JWT tokens 

 * 

 * DESIGN PATTERN: Singleton 

 * Single instance managed by Spring 

 */ 

@Component 

@Slf4j 

public class JwtUtil { 

 

    @Value("${jwt.secret}") 

    private String secret; 

 

    @Value("${jwt.expiration}") 

    private long expiration; 

 

    // Generate JWT token from email 

    public String generateToken(String email) { 

        log.debug( 

            "Generating token for: {}", email); 

        return Jwts.builder() 

                .setSubject(email) 

                .setIssuedAt(new Date()) 

                .setExpiration(new Date( 

                    System.currentTimeMillis() 

                    + expiration)) 

                .signWith(getSignKey(), 

                    SignatureAlgorithm.HS256) 

                .compact(); 

    } 

 

    // Extract email from token 

    public String extractEmail(String token) { 

        return Jwts.parserBuilder() 

                .setSigningKey(getSignKey()) 

                .build() 

                .parseClaimsJws(token) 

                .getBody() 

                .getSubject(); 

    } 

 

    // Validate token against email 

    public boolean validateToken( 

                String token, String email) { 

        try { 

            return extractEmail(token) 

                        .equals(email) 

                   && !isTokenExpired(token); 

        } catch (Exception e) { 

            log.error( 

                "Token validation failed: {}", 

                e.getMessage()); 

            return false; 

        } 

    } 

 

    // Check if token is expired 

    private boolean isTokenExpired( 

                            String token) { 

        return Jwts.parserBuilder() 

                .setSigningKey(getSignKey()) 

                .build() 

                .parseClaimsJws(token) 

                .getBody() 

                .getExpiration() 

                .before(new Date()); 

    } 

 

    // Build signing key from secret 

    private Key getSignKey() { 

        return Keys.hmacShaKeyFor( 

            Decoders.BASE64.decode(secret)); 

    } 

} 