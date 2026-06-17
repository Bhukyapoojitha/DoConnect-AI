package com.doconnect.design_patterns.strategy;

import java.util.List;

/** 

 * AIStrategy Interface 

 * DESIGN PATTERN: Strategy Pattern 

 * 

 * PURPOSE: Defines contract for AI providers. 

 * Allows switching between OpenAI and Gemini 

 * without changing business logic code. 

 */ 

public interface AIStrategy { 

    String generateAnswer( 

            String title, String content); 

    List<String> suggestTags( 

            String title, String content); 

    boolean isToxicContent(String content); 

    double analyzeSentiment(String content); 

} 