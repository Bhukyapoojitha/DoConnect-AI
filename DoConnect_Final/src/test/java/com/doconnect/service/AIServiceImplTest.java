package com.doconnect.service;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.doconnect.design_patterns.strategy.AIContext;
import com.doconnect.service.impl.AIServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/** 

 * Unit Tests for AIServiceImpl 

 * Tests AI delegation to AIContext 

 */ 

@ExtendWith(MockitoExtension.class) 

class AIServiceImplTest { 

 

    @Mock 

    private AIContext aiContext; 

 

    @InjectMocks 

    private AIServiceImpl aiService; 

 

    // ──────────────────────────── 

    // GENERATE ANSWER TESTS 

    // ──────────────────────────── 

 

    @Test 

    @DisplayName("Generate Answer - Success") 

    void testGenerateAnswer_Success() { 

 

        // ARRANGE 

        when(aiContext.generateAnswer( 

            "What is Spring Boot?", 

            "Explain Spring Boot")) 

            .thenReturn( 

                "Spring Boot is a framework..."); 

 

        // ACT 

        String result = aiService.generateAnswer( 

            "What is Spring Boot?", 

            "Explain Spring Boot"); 

 

        // ASSERT 

        assertNotNull(result); 

        assertFalse(result.isEmpty()); 

        assertEquals( 

            "Spring Boot is a framework...", 

            result); 

 

        verify(aiContext, times(1)) 

            .generateAnswer( 

                "What is Spring Boot?", 

                "Explain Spring Boot"); 

    } 

 

    @Test 

    @DisplayName("Generate Answer - AI Unavailable") 

    void testGenerateAnswer_Unavailable() { 

 

        // ARRANGE 

    	when(aiContext.generateAnswer("Question", "Content"))
        .thenReturn("AI service unavailable.");

 

        // ACT 

        String result = aiService.generateAnswer( 

            "Question", "Content"); 

 

        // ASSERT 

        assertEquals( 

            "AI service unavailable.", 

            result); 

    } 

 

    // ──────────────────────────── 

    // SUGGEST TAGS TESTS 

    // ──────────────────────────── 

 

    @Test 

    @DisplayName("Suggest Tags - Success") 

    void testSuggestTags_Success() { 

 

        // ARRANGE 

        List<String> expectedTags = 

            List.of("java", "spring-boot", 

                    "rest-api"); 

 

        when(aiContext.suggestTags( 

            "Spring Boot Question", 

            "Tell me about Spring Boot")) 

            .thenReturn(expectedTags); 

 

        // ACT 

        List<String> result = 

            aiService.suggestTags( 

                "Spring Boot Question", 

                "Tell me about Spring Boot"); 

 

        // ASSERT 

        assertNotNull(result); 

        assertEquals(3, result.size()); 

        assertTrue(result.contains("java")); 

        assertTrue( 

            result.contains("spring-boot")); 

 

        verify(aiContext).suggestTags(
        	    "Spring Boot Question",
        	    "Tell me about Spring Boot"
        	); 

    } 

 

    @Test 

    @DisplayName("Suggest Tags - Empty Result") 

    void testSuggestTags_Empty() { 

 

        // ARRANGE 

    	when(aiContext.suggestTags("Title", "Content"))
        .thenReturn(Collections.emptyList());

 

        // ACT 

        List<String> result = 

            aiService.suggestTags( 

                "Title", "Content"); 

 

        // ASSERT 

        assertNotNull(result); 

        assertTrue(result.isEmpty()); 

    } 

 

    // ──────────────────────────── 

    // IS TOXIC CONTENT TESTS 

    // ──────────────────────────── 

 

    @Test 

    @DisplayName("Is Toxic - Returns True") 

    void testIsToxicContent_True() { 

 

        // ARRANGE 

        when(aiContext.isToxicContent( 

            "bad offensive content")) 

            .thenReturn(true); 

 

        // ACT 

        boolean result = 

            aiService.isToxicContent( 

                "bad offensive content"); 

 

        // ASSERT 

        assertTrue(result); 

 

        verify(aiContext, times(1)) 

            .isToxicContent( 

                "bad offensive content"); 

    } 

 

    @Test 

    @DisplayName("Is Toxic - Returns False") 

    void testIsToxicContent_False() { 

 

        // ARRANGE 

        when(aiContext.isToxicContent( 

            "normal helpful content")) 

            .thenReturn(false); 

 

        // ACT 

        boolean result = 

            aiService.isToxicContent( 

                "normal helpful content"); 

 

        // ASSERT 

        assertFalse(result); 

    } 

 

    // ──────────────────────────── 

    // SENTIMENT ANALYSIS TESTS 

    // ──────────────────────────── 

 

    @Test 

    @DisplayName("Sentiment - Positive") 

    void testAnalyzeSentiment_Positive() { 

 

        // ARRANGE 

        when(aiContext.analyzeSentiment( 

            "Great answer! Very helpful!")) 

            .thenReturn(0.8); 

 

        // ACT 

        double result = 

            aiService.analyzeSentiment( 

                "Great answer! Very helpful!"); 

 

        // ASSERT 

        assertTrue(result > 0); 

        assertEquals(0.8, result, 0.01); 

 

        verify(aiContext).analyzeSentiment("Great answer! Very helpful!");

    } 

 

    @Test 

    @DisplayName("Sentiment - Negative") 

    void testAnalyzeSentiment_Negative() { 

 

        // ARRANGE 

        when(aiContext.analyzeSentiment( 

            "Terrible explanation!")) 

            .thenReturn(-0.7); 

 

        // ACT 

        double result = 

            aiService.analyzeSentiment( 

                "Terrible explanation!"); 

 

        // ASSERT 

        assertTrue(result < 0); 

        assertEquals(-0.7, result, 0.01); 

    } 

 

    @Test 

    @DisplayName("Sentiment - Neutral") 

    void testAnalyzeSentiment_Neutral() { 

 

        // ARRANGE 

        when(aiContext.analyzeSentiment( 

            "The code runs as expected")) 

            .thenReturn(0.0); 

 

        // ACT 

        double result = 

            aiService.analyzeSentiment( 

                "The code runs as expected"); 

 

        // ASSERT 

        assertEquals(0.0, result, 0.01); 

    } 

} 