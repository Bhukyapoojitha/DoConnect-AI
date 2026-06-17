package com.doconnect.design_patterns.factory;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/** 

 * ResponseFactory 

 * DESIGN PATTERN: Factory Pattern 

 * 

 * PURPOSE: Creates standardized API response 

 * objects without exposing creation logic. 

 * All controllers use this for consistent 

 * response format across the application. 

 */ 

@Component 

public class ResponseFactory { 

 

    // Success response with data 

    public static Map<String, Object> 

                success(String message, 

                        Object data) { 

        Map<String, Object> response = 

                            new HashMap<>(); 

        response.put("status", "success"); 

        response.put("message", message); 

        response.put("data", data); 

        response.put("timestamp", 

                    LocalDateTime.now()); 

        return response; 

    } 

 

    // Error response 

    public static Map<String, Object> 

                error(String message, int code) { 

        Map<String, Object> response = 

                            new HashMap<>(); 

        response.put("status", "error"); 

        response.put("message", message); 

        response.put("code", code); 

        response.put("timestamp", 

                    LocalDateTime.now()); 

        return response; 

    } 

 

    // AI response 

    public static Map<String, Object> 

                aiResponse(String answer, 

                           List<String> tags, 

                           double sentiment) { 

        Map<String, Object> response = 

                            new HashMap<>(); 

        response.put("status", "success"); 

        response.put("aiAnswer", answer); 

        response.put("suggestedTags", tags); 

        response.put("sentiment", sentiment); 

        response.put("generatedAt", 

                    LocalDateTime.now()); 

        return response; 

    } 

} 