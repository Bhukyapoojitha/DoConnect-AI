package com.doconnect.design_patterns.strategy;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.MediaType;

import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

/** 

 * GeminiStrategy 

 * DESIGN PATTERN: Strategy Pattern 

 * Concrete Strategy - Google Gemini AI 

 * 

 * PURPOSE: Implements AI operations 

 * using Google Gemini API 

 */ 

@Component("gemini") 

@Slf4j 

public class GeminiStrategy 

                implements AIStrategy { 

 

    @Value("${gemini.api.key}") 

    private String apiKey; 

 

    @Value("${gemini.api.url}") 

    private String apiUrl; 

 

    private final WebClient webClient; 

 

    public GeminiStrategy( 

                WebClient.Builder builder) { 

        this.webClient = builder.build(); 

    } 

 

    // Core method to call Gemini API 

    private String callGemini(String prompt) { 

        try { 

            Map<String, Object> part = 

                Map.of("text", prompt); 

            Map<String, Object> content = 

                Map.of("parts", List.of(part)); 

            Map<String, Object> requestBody = 

                Map.of("contents", 

                        List.of(content)); 

 

            Map response = webClient.post() 

                .uri(apiUrl + "?key=" + apiKey) 

                .contentType( 

                    MediaType.APPLICATION_JSON) 

                .bodyValue(requestBody) 

                .retrieve() 

                .bodyToMono(Map.class) 

                .block(); 

 

            // Parse Gemini response structure 

            List<Map> candidates = 

                (List<Map>) response 

                    .get("candidates"); 

            Map content0 = (Map) 

                candidates.get(0) 

                          .get("content"); 

            List<Map> parts = 

                (List<Map>) content0.get("parts"); 

 

            return parts.get(0) 

                        .get("text") 

                        .toString().trim(); 

 

        } catch (Exception e) { 

            log.error( 

                "Gemini API error: {}", 

                e.getMessage()); 

            return "AI service unavailable."; 

        } 

    } 

 

    @Override 

    public String generateAnswer( 

                String title, String content) { 

        String prompt = String.format(""" 

            You are a helpful tech assistant. 

            Answer this programming question 

            clearly with code examples if needed. 

            Title: %s 

            Question: %s 

            """, title, content); 

        return callGemini(prompt); 

    } 

 

    @Override 

    public List<String> suggestTags( 

                String title, String content) { 

        String prompt = String.format(""" 

            Suggest 3-5 relevant tags for this 

            programming question. 

            Return ONLY comma-separated tags. 

            Example: java,spring-boot,rest-api 

            Title: %s 

            Content: %s 

            """, title, content); 

 

        String result = callGemini(prompt); 

        return Arrays.stream(result.split(",")) 

                .map(String::trim) 

                .map(String::toLowerCase) 

                .filter(t -> !t.isEmpty()) 

                .collect(Collectors.toList()); 

    } 

 

    @Override 

    public boolean isToxicContent( 

                            String content) { 

        String prompt = String.format(""" 

            Is this content toxic or offensive? 

            Reply ONLY 'true' or 'false'. 

            Content: %s 

            """, content); 

        return callGemini(prompt) 

                .toLowerCase().contains("true"); 

    } 

 

    @Override 

    public double analyzeSentiment( 

                            String content) { 

        String prompt = String.format(""" 

            Analyze sentiment of this text. 

            Return ONLY a number: -1.0 to 1.0 

            Text: %s 

            """, content); 

        try { 

            return Double.parseDouble( 

                callGemini(prompt) 

                    .replaceAll("[^\\d.-]", "")); 

        } catch (Exception e) { 

            return 0.0; 

        } 

    } 

} 