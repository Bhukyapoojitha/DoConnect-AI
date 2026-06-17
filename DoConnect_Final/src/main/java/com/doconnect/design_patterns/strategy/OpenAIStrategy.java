package com.doconnect.design_patterns.strategy;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.MediaType;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

/** 

 * OpenAIStrategy 

 * DESIGN PATTERN: Strategy Pattern 

 * Concrete Strategy - OpenAI (Backup) 

 */ 

@Component("openAI") 

@Slf4j 

public class OpenAIStrategy 

                implements AIStrategy { 

 

    @Value("${openai.api.key}") 

    private String apiKey; 

 

    @Value("${openai.model}") 

    private String model; 

 

    private final WebClient webClient; 

 

    public OpenAIStrategy( 

                WebClient.Builder builder) { 

        this.webClient = builder.build(); 

    } 

 

    private String callOpenAI(String prompt) { 

        try { 

            Map<String, Object> requestBody = 

                new HashMap<>(); 

            requestBody.put("model", model); 

            requestBody.put("max_tokens", 500); 

            requestBody.put("messages", 

                List.of(Map.of( 

                    "role", "user", 

                    "content", prompt))); 

 

            Map response = webClient.post() 

                .uri("https://api.openai.com" + 

                     "/v1/chat/completions") 

                .header("Authorization", 

                    "Bearer " + apiKey) 

                .contentType( 

                    MediaType.APPLICATION_JSON) 

                .bodyValue(requestBody) 

                .retrieve() 

                .bodyToMono(Map.class) 

                .block(); 

 

            List<Map> choices = 

                (List<Map>) response.get("choices"); 

            Map message = (Map) 

                choices.get(0).get("message"); 

            return message.get("content") 

                          .toString().trim(); 

 

        } catch (Exception e) { 

            log.error( 

                "OpenAI error: {}", 

                e.getMessage()); 

            return "AI service unavailable."; 

        } 

    } 

 

    @Override 

    public String generateAnswer( 

                String title, String content) { 

        return callOpenAI( 

            "Answer: " + title + "\n" + content); 

    } 

 

    @Override 

    public List<String> suggestTags( 

                String title, String content) { 

        String result = callOpenAI( 

            "Tags for: " + title + 

            "\nReturn comma-separated only."); 

        return Arrays.stream(result.split(",")) 

                .map(String::trim) 

                .collect(Collectors.toList()); 

    } 

 

    @Override 

    public boolean isToxicContent( 

                            String content) { 

        return callOpenAI( 

            "Is toxic? Reply true/false: " 

            + content) 

            .contains("true"); 

    } 

 

    @Override 

    public double analyzeSentiment( 

                            String content) { 

        try { 

            return Double.parseDouble( 

                callOpenAI( 

                    "Sentiment -1 to 1: " 

                    + content) 

                .replaceAll("[^\\d.-]", "")); 

        } catch (Exception e) { 

            return 0.0; 

        } 

    } 

} 