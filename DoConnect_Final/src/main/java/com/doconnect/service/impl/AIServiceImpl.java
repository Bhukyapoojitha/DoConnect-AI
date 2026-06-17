/** 

 * AIServiceImpl 

 * Core AI service using Strategy Pattern 

 * Delegates to AIContext for provider selection 

 * 

 * DESIGN PATTERN: Strategy (via AIContext) 

 * DESIGN PATTERN: Proxy (via AIServiceProxy) 

 */ 
package com.doconnect.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import com.doconnect.design_patterns.strategy.AIContext;
import com.doconnect.service.interfaces.AIService;


@Service 

@RequiredArgsConstructor 

@Slf4j 

public class AIServiceImpl 

                implements AIService { 

 

    // Uses Strategy Pattern to pick provider 

    private final AIContext aiContext; 

 

    @Override 

    public String generateAnswer( 

                String title, String content) { 

        log.info( 

            "Generating AI answer for: {}", 

            title); 

        return aiContext.generateAnswer( 

                            title, content); 

    } 

 

    @Override 

    public List<String> suggestTags( 

                String title, String content) { 

        log.info( 

            "Suggesting tags for: {}", title); 

        return aiContext.suggestTags( 

                            title, content); 

    } 

 

    @Override 

    public boolean isToxicContent( 

                            String content) { 

        log.info("Checking content moderation"); 

        return aiContext.isToxicContent(content); 

    } 

 

    @Override 

    public double analyzeSentiment( 

                            String content) { 

        log.info("Analyzing sentiment"); 

        return aiContext.analyzeSentiment(content); 

    } 

} 