package com.doconnect.controller;

/**
 * AnalyticsController
 * Admin only dashboard analytics
 */
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.security.access.prepost.PreAuthorize;

import com.doconnect.service.interfaces.AnalyticsService;
import com.doconnect.design_patterns.factory.ResponseFactory;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Analytics", description = "Admin Analytics APIs")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/dashboard")
    @Operation(summary = "Get dashboard stats")
    public ResponseEntity<Map<String, Object>> getDashboard() {

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "Dashboard stats",
                        analyticsService.getDashboardStats()));
    }

    @GetMapping("/trending")
    @Operation(summary = "Get trending topics")
    public ResponseEntity<Map<String, Object>> getTrending() {

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "Trending topics",
                        analyticsService.getTrendingTopics()));
    }

    @GetMapping("/active-users")
    @Operation(summary = "Get active users")
    public ResponseEntity<Map<String, Object>> getActiveUsers() {

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "Active users",
                        analyticsService.getActiveUsers()));
    }
}
