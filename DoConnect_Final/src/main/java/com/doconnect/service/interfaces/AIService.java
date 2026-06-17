package com.doconnect.service.interfaces;

import java.util.List;

public interface AIService { 

    String generateAnswer( 

            String title, String content); 

    List<String> suggestTags( 

            String title, String content); 

    boolean isToxicContent(String content); 

    double analyzeSentiment(String content); 

} 