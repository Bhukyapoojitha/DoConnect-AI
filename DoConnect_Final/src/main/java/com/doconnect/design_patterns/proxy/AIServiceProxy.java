/** 
 * AIServiceProxy 
 * DESIGN PATTERN: Proxy Pattern 
 * PURPOSE: Caches AI responses to avoid 
 * repeated expensive API calls for same 
 * questions. Acts as proxy to real AI service. 
 */ 
package com.doconnect.design_patterns.proxy;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.doconnect.service.interfaces.AIService;
import com.doconnect.service.impl.AIServiceImpl;

@Service 

@Primary 

@Slf4j 

public class AIServiceProxy 

                implements AIService { 

 

    private final AIServiceImpl realAIService; 

 

    // In-memory cache for answers and tags 

    private final Map<String, String> 

        answerCache = new ConcurrentHashMap<>(); 

    private final Map<String, List<String>> 

        tagCache = new ConcurrentHashMap<>(); 

 

    public AIServiceProxy( 

                AIServiceImpl realAIService) { 

        this.realAIService = realAIService; 

    } 

 

    @Override 

    public String generateAnswer( 

                String title, String content) { 

 

        // Create cache key from title 

        String key = title.toLowerCase() 

                .trim().replaceAll("\\s+", "_"); 

 

        // Return cached if exists 

        if (answerCache.containsKey(key)) { 

            log.info( 

                "Cache HIT for answer: {}", key); 

            return answerCache.get(key); 

        } 

 

        // Call real service 

        log.info( 

            "Cache MISS - calling Gemini: {}", 

            key); 

        String answer = realAIService 

                    .generateAnswer(title, content); 

 

        // Store in cache 

        answerCache.put(key, answer); 

        return answer; 

    } 

 

    @Override 

    public List<String> suggestTags( 

                String title, String content) { 

 

        String key = title.toLowerCase() 

                .trim().replaceAll("\\s+", "_"); 

 

        if (tagCache.containsKey(key)) { 

            log.info( 

                "Cache HIT for tags: {}", key); 

            return tagCache.get(key); 

        } 

 

        List<String> tags = realAIService 

                    .suggestTags(title, content); 

        tagCache.put(key, tags); 

        return tags; 

    } 

 

    @Override 

    public boolean isToxicContent( 

                            String content) { 

        // Never cache - always check fresh 

        return realAIService 

                    .isToxicContent(content); 

    } 

 

    @Override 

    public double analyzeSentiment( 

                            String content) { 

        return realAIService 

                    .analyzeSentiment(content); 

    } 

} 