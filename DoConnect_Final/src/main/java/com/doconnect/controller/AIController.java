 package com.doconnect.controller;

/**
 * AIController
 * Handles AI feature endpoints
 * Answer generation, tags, moderation
 */
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.List;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import com.doconnect.service.interfaces.AIService;
import com.doconnect.repository.QuestionRepository;
import com.doconnect.entity.Question;
import com.doconnect.dto.ai.AIRequestDTO;
import com.doconnect.exception.ResourceNotFoundException;
import com.doconnect.design_patterns.factory.ResponseFactory;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@Tag(name = "AI Features", description = "Gemini AI Integration APIs")
public class AIController {

    private final AIService aiService;
    private final QuestionRepository questionRepository;

    // Generate AI answer for question
    @PostMapping("/answer/{questionId}")
    @Operation(summary = "Generate AI answer")
    public ResponseEntity<Map<String, Object>> generateAnswer(
            @PathVariable Long questionId) {

        Question question = questionRepository
                .findById(questionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Question not found"));

        String answer = aiService.generateAnswer(
                question.getTitle(),
                question.getContent());

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "AI answer generated!",
                        Map.of("answer", answer)));
    }

    // Suggest tags for question
    @PostMapping("/tags")
    @Operation(summary = "Suggest AI tags")
    public ResponseEntity<Map<String, Object>> suggestTags(
            @Valid @RequestBody AIRequestDTO request) {

        List<String> tags = aiService.suggestTags(
                request.getQuestionTitle(),
                request.getQuestionContent());

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "Tags suggested!",
                        Map.of("suggestedTags", tags)));
    }

    // Check content toxicity
    @PostMapping("/moderate")
    @Operation(summary = "Moderate content")
    public ResponseEntity<Map<String, Object>> moderate(
            @RequestBody Map<String, String> body) {

        String content = body.get("content");

        boolean isToxic = aiService.isToxicContent(content);

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "Moderation complete",
                        Map.of(
                                "isToxic", isToxic,
                                "message", isToxic
                                        ? "Content violates guidelines"
                                        : "Content is appropriate"
                        )));
    }

    // Analyze sentiment
    @PostMapping("/sentiment")
    @Operation(summary = "Analyze content sentiment")
    public ResponseEntity<Map<String, Object>> sentiment(
            @RequestBody Map<String, String> body) {

        double score = aiService.analyzeSentiment(
                body.get("content"));

        String label = score > 0.3 ? "Positive"
                : score < -0.3 ? "Negative"
                : "Neutral";

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "Sentiment analyzed",
                        Map.of(
                                "score", score,
                                "label", label
                        )));
    }
}