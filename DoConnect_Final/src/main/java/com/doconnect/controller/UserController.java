/** 

 * UserController 

 * Handles user profile endpoints 

 * All endpoints require authentication 

 */ 
package com.doconnect.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import com.doconnect.service.interfaces.UserService;

import com.doconnect.dto.user.UserProfileDTO;
import com.doconnect.dto.user.UpdateProfileDTO;
import com.doconnect.dto.user.ChangePasswordDTO;

import com.doconnect.design_patterns.factory.ResponseFactory;

@RestController 

@RequestMapping("/api/users") 

@RequiredArgsConstructor 

@Tag(name = "Users", 

     description = "User Profile APIs") 

public class UserController { 

 

    private final UserService userService; 

 

    // Get own profile 

    @GetMapping("/profile") 

    @Operation(summary = "Get own profile") 

    public ResponseEntity<Map<String, Object>> 

                getProfile( 

                @AuthenticationPrincipal 

                UserDetails userDetails) { 

 

        UserProfileDTO profile = 

            userService.getProfile( 

                userDetails.getUsername()); 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Profile fetched", 

                    profile)); 

    } 

 

    // Update own profile 

    @PutMapping("/profile") 

    @Operation(summary = "Update own profile") 

    public ResponseEntity<Map<String, Object>> 

                updateProfile( 

                @Valid @RequestBody 

                UpdateProfileDTO dto, 

                @AuthenticationPrincipal 

                UserDetails userDetails) { 

 

        UserProfileDTO updated = 

            userService.updateProfile( 

                userDetails.getUsername(), dto); 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Profile updated!", 

                    updated)); 

    } 

 

    // Change password 

    @PutMapping("/password") 

    @Operation(summary = "Change password") 

    public ResponseEntity<Map<String, Object>> 

                changePassword( 

                @Valid @RequestBody 

                ChangePasswordDTO dto, 

                @AuthenticationPrincipal 

                UserDetails userDetails) { 

 

        userService.changePassword( 

            userDetails.getUsername(), dto); 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Password changed!", null)); 

    } 

 

    // Get any user by ID 

    @GetMapping("/{id}") 

    @Operation(summary = "Get user by ID") 

    public ResponseEntity<Map<String, Object>> 

                getUserById( 

                @PathVariable Long id) { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "User fetched", 

                    userService.getUserById(id))); 

    } 

 

    // Get user's questions 

    @GetMapping("/{id}/questions") 

    @Operation( 

        summary = "Get user's questions") 

    public ResponseEntity<Map<String, Object>> 

                getUserQuestions( 

                @PathVariable Long id) { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Questions fetched", 

                    userService 

                        .getUserQuestions(id))); 

    } 

 

    // Get user's answers 

    @GetMapping("/{id}/answers") 

    @Operation(summary = "Get user's answers") 

    public ResponseEntity<Map<String, Object>> 

                getUserAnswers( 

                @PathVariable Long id) { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Answers fetched", 

                    userService 

                        .getUserAnswers(id))); 

    } 

 

    // Delete own account 

    @DeleteMapping("/account") 

    @Operation(summary = "Delete own account") 

    public ResponseEntity<Map<String, Object>> 

                deleteAccount( 

                @AuthenticationPrincipal 

                UserDetails userDetails) { 

 

        userService.deleteAccount( 

            userDetails.getUsername()); 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Account deleted!", null)); 

    } 

} 