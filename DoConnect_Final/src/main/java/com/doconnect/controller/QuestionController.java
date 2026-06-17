/** 

 * QuestionController 

 * Handles all question CRUD endpoints 

 * Includes search, trending, tag filter 

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

import com.doconnect.service.interfaces.QuestionService;

import com.doconnect.dto.question.QuestionRequestDTO;

import com.doconnect.design_patterns.factory.ResponseFactory;


@RestController 

@RequestMapping("/api/questions") 

@RequiredArgsConstructor 

@Tag(name = "Questions", 

     description = "Question Management APIs") 

public class QuestionController { 

 

    private final QuestionService 

                        questionService; 

 

    @PostMapping 

    @Operation(summary = "Create new question") 

    public ResponseEntity<Map<String, Object>> 

                create( 

                @Valid @RequestBody 

                QuestionRequestDTO dto, 

                @AuthenticationPrincipal 

                UserDetails userDetails) { 

 

        return ResponseEntity 

                .status(HttpStatus.CREATED) 

                .body(ResponseFactory.success( 

                    "Question created!", 

                    questionService.createQuestion( 

                        dto, 

                        userDetails.getUsername()))); 

    } 

 

    @GetMapping 

    @Operation(summary = "Get all questions") 

    public ResponseEntity<Map<String, Object>> 

                getAll() { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Questions fetched", 

                    questionService 

                        .getAllQuestions())); 

    } 

 

    @GetMapping("/{id}") 

    @Operation(summary = "Get question by ID") 

    public ResponseEntity<Map<String, Object>> 

                getById( 

                @PathVariable Long id) { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Question fetched", 

                    questionService 

                        .getQuestionById(id))); 

    } 

 

    @PutMapping("/{id}") 

    @Operation(summary = "Update question") 

    public ResponseEntity<Map<String, Object>> 

                update( 

                @PathVariable Long id, 

                @Valid @RequestBody 

                QuestionRequestDTO dto, 

                @AuthenticationPrincipal 

                UserDetails userDetails) { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Question updated!", 

                    questionService.updateQuestion( 

                        id, dto, 

                        userDetails.getUsername()))); 

    } 

 

    @DeleteMapping("/{id}") 

    @Operation(summary = "Delete question") 

    public ResponseEntity<Map<String, Object>> 

                delete( 

                @PathVariable Long id, 

                @AuthenticationPrincipal 

                UserDetails userDetails) { 

 

        questionService.deleteQuestion( 

            id, userDetails.getUsername()); 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Question deleted!", null)); 

    } 

 

    @GetMapping("/search") 

    @Operation(summary = "Search questions") 

    public ResponseEntity<Map<String, Object>> 

                search( 

                @RequestParam String keyword) { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Search results", 

                    questionService 

                        .searchQuestions(keyword))); 

    } 

 

    @GetMapping("/trending") 

    @Operation( 

        summary = "Get trending questions") 

    public ResponseEntity<Map<String, Object>> 

                trending() { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Trending questions", 

                    questionService 

                        .getTrendingQuestions())); 

    } 

 

    @GetMapping("/tag/{tagName}") 

    @Operation( 

        summary = "Get questions by tag") 

    public ResponseEntity<Map<String, Object>> 

                byTag( 

                @PathVariable String tagName) { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Questions by tag", 

                    questionService 

                        .getQuestionsByTag( 

                            tagName))); 

    } 

 

    @PutMapping("/{id}/vote") 

    @Operation(summary = "Vote on question") 

    public ResponseEntity<Map<String, Object>> 

                vote( 

                @PathVariable Long id, 

                @RequestParam String type) { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Vote recorded!", 

                    questionService 

                        .voteQuestion(id, type))); 

    } 

}
