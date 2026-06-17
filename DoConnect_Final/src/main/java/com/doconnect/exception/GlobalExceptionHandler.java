package com.doconnect.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.dao.DataIntegrityViolationException;

import jakarta.validation.ConstraintViolationException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.HashMap;


/** 

 * GlobalExceptionHandler 

 * Handles all exceptions across the application 

 * Returns consistent error responses 

 * 

 * DESIGN PATTERN: Singleton 

 * Single handler for all controllers 

 */ 

@RestControllerAdvice 

@Slf4j 

public class GlobalExceptionHandler { 

 

    // Handle DTO field validation errors 

    @ExceptionHandler( 

        MethodArgumentNotValidException.class) 

    public ResponseEntity<Map<String, String>> 

            handleValidation( 

            MethodArgumentNotValidException ex) { 

 

        Map<String, String> errors = 

                                new HashMap<>(); 

        ex.getBindingResult() 

          .getFieldErrors() 

          .forEach(err -> errors.put( 

                err.getField(), 

                err.getDefaultMessage())); 

 

        log.warn("Validation failed: {}", errors); 

        return ResponseEntity 

                .badRequest() 

                .body(errors); 

    } 

 

    // Handle constraint violations 

    @ExceptionHandler( 

        ConstraintViolationException.class) 

    public ResponseEntity<Map<String, String>> 

            handleConstraint( 

            ConstraintViolationException ex) { 

 

        Map<String, String> errors = 

                                new HashMap<>(); 

        ex.getConstraintViolations() 

          .forEach(v -> errors.put( 

                v.getPropertyPath().toString(), 

                v.getMessage())); 

 

        return ResponseEntity 

                .badRequest() 

                .body(errors); 

    } 

 

    // Handle duplicate DB entries 

    @ExceptionHandler( 

        DataIntegrityViolationException.class) 

    public ResponseEntity<String> 

            handleDuplicate( 

            DataIntegrityViolationException ex) { 

 

        log.error("DB integrity error: {}", 

                    ex.getMessage()); 

        return ResponseEntity 

                .status(HttpStatus.CONFLICT) 

                .body("Email or username " + 

                      "already exists!"); 

    } 

 

    // Handle resource not found 

    @ExceptionHandler( 

        ResourceNotFoundException.class) 

    public ResponseEntity<String> 

            handleNotFound( 

            ResourceNotFoundException ex) { 

 

        log.warn("Resource not found: {}", 

                    ex.getMessage()); 

        return ResponseEntity 

                .status(HttpStatus.NOT_FOUND) 

                .body(ex.getMessage()); 

    } 

 

    // Handle user already exists 

    @ExceptionHandler( 

        UserAlreadyExistsException.class) 

    public ResponseEntity<String> 

            handleUserExists( 

            UserAlreadyExistsException ex) { 

 

        return ResponseEntity 

                .status(HttpStatus.CONFLICT) 

                .body(ex.getMessage()); 

    } 

 

    // Handle bad credentials 

    @ExceptionHandler( 

        BadCredentialsException.class) 

    public ResponseEntity<String> 

            handleBadCredentials( 

            BadCredentialsException ex) { 

 

        return ResponseEntity 

                .status(HttpStatus.UNAUTHORIZED) 

                .body("Invalid email or password!"); 

    } 

 

    // Handle access denied 

    @ExceptionHandler(AccessDeniedException.class) 

    public ResponseEntity<String> 

            handleAccessDenied( 

            AccessDeniedException ex) { 

 

        return ResponseEntity 

                .status(HttpStatus.FORBIDDEN) 

                .body("Access denied!"); 

    } 

 

    // Handle all other exceptions 

    @ExceptionHandler(Exception.class) 

    public ResponseEntity<String> handleAll( 

                Exception ex) { 

 

        log.error("Unexpected error: {}", 

                    ex.getMessage()); 

        return ResponseEntity 

                .status( 

                    HttpStatus.INTERNAL_SERVER_ERROR) 

                .body("Something went wrong. " + 

                      "Please try again."); 

    } 

} 