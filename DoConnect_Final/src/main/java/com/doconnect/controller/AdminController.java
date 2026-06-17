package com.doconnect.controller;

/**
 * AdminController
 * Admin only user management
 */
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.security.access.prepost.PreAuthorize;

import com.doconnect.service.interfaces.UserService;
import com.doconnect.enums.Role;

import com.doconnect.design_patterns.factory.ResponseFactory;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin", description = "Admin User Management APIs")
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    @Operation(summary = "Get all users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "All users fetched",
                        userService.getAllUsers()));
    }

    @PutMapping("/users/{id}/role")
    @Operation(summary = "Update user role")
    public ResponseEntity<Map<String, Object>> updateRole(
            @PathVariable Long id,
            @RequestParam Role role) {

        userService.updateUserRole(id, role);

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "Role updated!", null));
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete user")
    public ResponseEntity<Map<String, Object>> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "User deleted!", null));
    }
}