package com.doconnect.controller;

/**
 * ModerationController
 * Admin and Moderator content review
 */
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.security.access.prepost.PreAuthorize;

import com.doconnect.service.interfaces.ModerationService;

import com.doconnect.enums.ModerationStatus;

import com.doconnect.design_patterns.factory.ResponseFactory;

@RestController
@RequestMapping("/api/moderation")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
@Tag(name = "Moderation", description = "Content Moderation APIs")
public class ModerationController {

    private final ModerationService moderationService;

    
    @GetMapping("/stats")
    @Operation(summary = "Moderation statistics")
    public ResponseEntity<Map<String, Object>> getStats() {

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "Moderation stats",
                        moderationService.getModerationStats()
                )
        );
    }
    
    @GetMapping("/pending")
    @Operation(summary = "Get pending content")
    public ResponseEntity<Map<String, Object>> getPending() {

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "Pending content",
                        moderationService.getPendingContent()));
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "Approve content")
    public ResponseEntity<Map<String, Object>> approve(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "Content approved!",
                        moderationService.updateStatus(
                                id,
                                ModerationStatus.APPROVED)));
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "Reject content")
    public ResponseEntity<Map<String, Object>> reject(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "Content rejected!",
                        moderationService.updateStatus(
                                id,
                                ModerationStatus.REJECTED)));
    }

    @PostMapping("/flag")
    @Operation(summary = "Flag content")
    public ResponseEntity<Map<String, Object>> flag(
            @RequestBody Map<String, String> body) {

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "Content flagged!",
                        moderationService.flagContent(
                                body.get("contentType"),
                                Long.parseLong(body.get("contentId")),
                                body.get("content"),
                                body.get("reason"))));
    }
}