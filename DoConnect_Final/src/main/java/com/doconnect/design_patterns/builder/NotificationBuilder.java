package com.doconnect.design_patterns.builder;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Component;

import java.util.HashMap;

 

/** 

 * NotificationBuilder 

 * DESIGN PATTERN: Builder Pattern 

 * 

 * PURPOSE: Builds complex notification objects 

 * step by step. Used when new answers posted, 

 * content flagged, or users join chat. 

 */ 

@Component 

public class NotificationBuilder { 

 

    private String title; 

    private String message; 

    private String type; 

    private String recipient; 

    private Long referenceId; 

 

    public NotificationBuilder title( 

                                String title) { 

        this.title = title; 

        return this; 

    } 

 

    public NotificationBuilder message( 

                                String message) { 

        this.message = message; 

        return this; 

    } 

 

    public NotificationBuilder type( 

                                String type) { 

        this.type = type; 

        return this; 

    } 

 

    public NotificationBuilder recipient( 

                                String recipient) { 

        this.recipient = recipient; 

        return this; 

    } 

 

    public NotificationBuilder referenceId( 

                                Long id) { 

        this.referenceId = id; 

        return this; 

    } 

 

    // Build final notification object 

    public Map<String, Object> build() { 

        Map<String, Object> notification = 

                                new HashMap<>(); 

        notification.put("title", title); 

        notification.put("message", message); 

        notification.put("type", type); 

        notification.put("recipient", recipient); 

        notification.put("referenceId", 

                                    referenceId); 

        notification.put("createdAt", 

                        LocalDateTime.now()); 

        return notification; 

    } 

} 
