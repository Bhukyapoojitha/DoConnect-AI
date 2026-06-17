/** 

 * AnswerController 

 * Handles all answer endpoints 

 * Including accept and vote 

 */ 

package com.doconnect.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import com.doconnect.service.interfaces.AnswerService;

import com.doconnect.dto.answer.AnswerRequestDTO;

import com.doconnect.design_patterns.factory.ResponseFactory;

@RestController 

@RequestMapping("/api/answers") 

@RequiredArgsConstructor 

@Tag(name = "Answers", 

     description = "Answer Management APIs") 

public class AnswerController { 

 

    private final AnswerService answerService; 

 

    @PostMapping 

    @Operation(summary = "Post an answer") 

    public ResponseEntity<Map<String, Object>> 

                post( 

                @Valid @RequestBody 

                AnswerRequestDTO dto, 

                @AuthenticationPrincipal 

                UserDetails userDetails) { 

 

        return ResponseEntity 

                .status(HttpStatus.CREATED) 

                .body(ResponseFactory.success( 

                    "Answer posted!", 

                    answerService.postAnswer( 

                        dto, 

                        userDetails.getUsername()))); 

    } 

 

    @GetMapping("/{questionId}") 

    @Operation( 

        summary = "Get answers by question") 

    public ResponseEntity<Map<String, Object>> 

                getByQuestion( 

                @PathVariable Long questionId) { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Answers fetched", 

                    answerService 

                        .getAnswersByQuestion( 

                            questionId))); 

    } 

 

    @PutMapping("/{id}") 

    @Operation(summary = "Update answer") 

    public ResponseEntity<Map<String, Object>> 

                update( 

                @PathVariable Long id, 

                @Valid @RequestBody 

                AnswerRequestDTO dto, 

                @AuthenticationPrincipal 

                UserDetails userDetails) { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Answer updated!", 

                    answerService.updateAnswer( 

                        id, dto, 

                        userDetails.getUsername()))); 

    } 

 

    @DeleteMapping("/{id}") 

    @Operation(summary = "Delete answer") 

    public ResponseEntity<Map<String, Object>> 

                delete( 

                @PathVariable Long id, 

                @AuthenticationPrincipal 

                UserDetails userDetails) { 

 

        answerService.deleteAnswer( 

            id, userDetails.getUsername()); 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Answer deleted!", null)); 

    } 

 

    @PutMapping("/{id}/accept") 

    @Operation(summary = "Accept an answer") 

    public ResponseEntity<Map<String, Object>> 

                accept( 

                @PathVariable Long id, 

                @AuthenticationPrincipal 

                UserDetails userDetails) { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Answer accepted!", 

                    answerService.acceptAnswer( 

                        id, 

                        userDetails.getUsername()))); 

    } 

 

    @PutMapping("/{id}/vote") 

    @Operation(summary = "Vote on answer") 

    public ResponseEntity<Map<String, Object>> 

                vote( 

                @PathVariable Long id, 

                @RequestParam String type) { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Vote recorded!", 

                    answerService 

                        .voteAnswer(id, type))); 

    } 

} 
