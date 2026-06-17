/** 

 * EventPublisher 

 * DESIGN PATTERN: Observer Pattern - Publisher 

 * 

 * PURPOSE: Notifies ALL registered observers 

 * when system events occur. Services call this 

 * after important operations. 

 */ 
package com.doconnect.design_patterns.observer;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Component 

@RequiredArgsConstructor 

@Slf4j 

public class EventPublisher { 

 

    // All EventObserver beans auto-injected 

    private final List<EventObserver> observers; 

 

    // Publish event to all observers 

    public void publishEvent(String eventType, 

                             Object data) { 

        log.debug( 

            "Publishing event: {}", eventType); 

        observers.forEach(observer -> 

            observer.onEvent(eventType, data)); 

    } 

} 