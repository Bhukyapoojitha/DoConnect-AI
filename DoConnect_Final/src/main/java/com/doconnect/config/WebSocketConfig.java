package com.doconnect.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;

import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

 

/** 

 * WebSocketConfig 

 * Configures WebSocket messaging broker 

 * Enables real-time chat functionality 

 */ 

@Configuration 

@EnableWebSocketMessageBroker 

public class WebSocketConfig implements 

        WebSocketMessageBrokerConfigurer { 

 

    @Override 

    public void configureMessageBroker( 

            MessageBrokerRegistry registry) { 

 

        // Clients subscribe to /topic/* 

        registry.enableSimpleBroker("/topic"); 

 

        // Client sends to /app/* 

        registry.setApplicationDestinationPrefixes( 

                                            "/app"); 

    } 

 

    @Override 

    public void registerStompEndpoints( 

            StompEndpointRegistry registry) { 

 

        // WebSocket endpoint with SockJS fallback 

        registry.addEndpoint("/ws") 

                .setAllowedOriginPatterns("*") 

                .withSockJS(); 

    } 

} 