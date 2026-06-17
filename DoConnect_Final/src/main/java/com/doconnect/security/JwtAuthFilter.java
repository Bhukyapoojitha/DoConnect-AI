package com.doconnect.security;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

/** 

 * JwtAuthFilter 

 * Intercepts every HTTP request 

 * Validates JWT token and sets authentication 

 * 

 * DESIGN PATTERN: Proxy Pattern 

 * Acts as proxy between request and controller 

 */ 

@Component 

@RequiredArgsConstructor 

@Slf4j 

public class JwtAuthFilter 

            extends OncePerRequestFilter { 

 

    private final JwtUtil jwtUtil; 

    private final UserDetailsService 

                        userDetailsService; 

 

    @Override 

    protected void doFilterInternal( 

            HttpServletRequest request, 

            HttpServletResponse response, 

            FilterChain filterChain) 

            throws ServletException, IOException { 

 

        // Get Authorization header 

        String authHeader = request 

                .getHeader("Authorization"); 

 

        // Skip if no bearer token 

        if (authHeader == null || 

            !authHeader.startsWith("Bearer ")) { 

            filterChain.doFilter( 

                    request, response); 

            return; 

        } 

 

        // Extract token 

        String token = authHeader.substring(7); 

        String email = null; 

 

        try { 

            email = jwtUtil.extractEmail(token); 

        } catch (Exception e) { 

            log.error( 

                "JWT extraction failed: {}", 

                e.getMessage()); 

            filterChain.doFilter( 

                    request, response); 

            return; 

        } 

 

        // Set authentication if valid 

        if (email != null && 

            SecurityContextHolder.getContext() 

                .getAuthentication() == null) { 

 

            UserDetails userDetails = 

                userDetailsService 

                    .loadUserByUsername(email); 

 

            if (jwtUtil.validateToken( 

                        token, email)) { 

 

                UsernamePasswordAuthenticationToken 

                    authToken = 

                    new UsernamePasswordAuthenticationToken( 

                        userDetails, 

                        null, 

                        userDetails.getAuthorities()); 

 

                authToken.setDetails( 

                    new WebAuthenticationDetailsSource() 

                        .buildDetails(request)); 

 

                SecurityContextHolder 

                    .getContext() 

                    .setAuthentication(authToken); 

 

                log.debug( 

                    "Auth set for user: {}", 

                    email); 

            } 

        } 

 

        filterChain.doFilter(request, response); 

    } 

} 
