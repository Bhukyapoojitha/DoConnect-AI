package com.doconnect.design_patterns.observer;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/** 

 * LoggingObserver 

 * DESIGN PATTERN: Observer Pattern 

 * 

 * PURPOSE: Logs all system events 

 * for audit trail and debugging. 

 */ 

@Component 

@Slf4j 

public class LoggingObserver 

                implements EventObserver { 

 

    @Override 

    public void onEvent(String eventType, 

                        Object data) { 

        log.info( 

            "[AUDIT] Event: {} | Data: {} | " + 

            "Time: {}", 

            eventType, 

            data, 

            LocalDateTime.now()); 

    } 

} 
