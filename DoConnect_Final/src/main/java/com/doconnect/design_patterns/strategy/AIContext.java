package com.doconnect.design_patterns.strategy;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import com.doconnect.design_patterns.strategy.AIStrategy;

/** 

 * AIContext 

 * DESIGN PATTERN: Strategy Pattern - Context 

 * 

 * PURPOSE: Selects AI strategy based on 

 * application.properties ai.provider setting. 

 * Switch between Gemini/OpenAI in one line. 

 */ 

@Service 

@Slf4j 

public class AIContext { 

 

    @Value("${ai.provider:gemini}") 

    private String provider; 

 

    @Autowired 

    private Map<String, AIStrategy> strategies; 

 

    private AIStrategy strategy; 

 

    // Set strategy after bean creation 

    @PostConstruct 

    public void init() { 

        this.strategy = strategies.get(provider); 

        log.info( 

            "AI Provider selected: {}", provider); 

    } 

 

    public String generateAnswer( 

                String title, String content) { 

        return strategy.generateAnswer( 

                            title, content); 

    } 

 

    public List<String> suggestTags( 

                String title, String content) { 

        return strategy.suggestTags( 

                            title, content); 

    } 

 

    public boolean isToxicContent( 

                            String content) { 

        return strategy.isToxicContent(content); 

    } 

 

    public double analyzeSentiment( 

                            String content) { 

        return strategy.analyzeSentiment(content); 

    } 

}