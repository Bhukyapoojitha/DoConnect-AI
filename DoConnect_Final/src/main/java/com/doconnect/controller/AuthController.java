/** 

 * AuthController 

 * Handles registration and login endpoints 

 * Public endpoints - no auth required 

 */ 
package com.doconnect.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import com.doconnect.service.interfaces.AuthService;

import com.doconnect.dto.auth.RegisterRequestDTO;
import com.doconnect.dto.auth.LoginRequestDTO;
import com.doconnect.dto.auth.AuthResponseDTO;

import com.doconnect.design_patterns.factory.ResponseFactory;
@RestController 

@RequestMapping("/api/auth") 

@RequiredArgsConstructor 

@Tag(name = "Authentication", 

     description = "Register and Login APIs") 

public class AuthController { 

 

    private final AuthService authService; 

 

    // Register new user 

    @PostMapping("/register") 

    @Operation(summary = "Register new user") 

    public ResponseEntity<Map<String, Object>> 

                register( 

                @Valid @RequestBody 

                RegisterRequestDTO request) { 

 

        AuthResponseDTO response = 

                authService.register(request); 

 

        return ResponseEntity 

                .status(HttpStatus.CREATED) 

                .body(ResponseFactory.success( 

                    "Registration successful!", 

                    response)); 

    } 

 

    // Login existing user 

    @PostMapping("/login") 

    @Operation(summary = "Login user") 

    public ResponseEntity<Map<String, Object>> 

                login( 

                @Valid @RequestBody 

                LoginRequestDTO request) { 

 

        AuthResponseDTO response = 

                authService.login(request); 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Login successful!", 

                    response)); 

    } 

} 
