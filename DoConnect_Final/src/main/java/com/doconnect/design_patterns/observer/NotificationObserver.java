package com.doconnect.design_patterns.observer;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;



 

/** 

 * NotificationObserver 

 * DESIGN PATTERN: Observer Pattern 

 * 

 * PURPOSE: Handles notifications when 

 * events like new answers or flags occur. 

 */ 

@Component 

@Slf4j 

public class NotificationObserver 

                implements EventObserver { 

 

    @Override 

    public void onEvent(String eventType, 

                        Object data) { 

        switch (eventType) { 

            case "ANSWER_POSTED" -> 

                log.info( 

                    "NOTIFY: New answer posted {}", 

                    data); 

            case "QUESTION_FLAGGED" -> 

                log.info( 

                    "NOTIFY: Content flagged {}", 

                    data); 

            case "USER_JOINED_CHAT" -> 

                log.info( 

                    "NOTIFY: User joined {}", 

                    data); 

            default -> 

                log.info( 

                    "NOTIFY: Event {} - {}", 

                    eventType, data); 

        } 

    } 

} 

 