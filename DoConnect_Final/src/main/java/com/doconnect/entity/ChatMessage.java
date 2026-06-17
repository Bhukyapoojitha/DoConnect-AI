package com.doconnect.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import com.doconnect.enums.MessageType;
import lombok.*;

import java.time.LocalDateTime;

/** 

 * ChatMessage Entity 

 * Maps to 'chat_messages' table 

 * Stores WebSocket chat history 

 * 

 * VALIDATIONS: 

 * - Sender: required 

 * - Content: required, max 500 chars 

 * - RoomId: required 

 */ 

@Entity 

@Table(name = "chat_messages") 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

@Builder 

public class ChatMessage { 

 

    @Id 

    @GeneratedValue( 

        strategy = GenerationType.IDENTITY) 

    private Long id; 

 

    // Who sent the message 

    @NotBlank(message = "Sender is required") 

    @Column(nullable = false) 

    private String sender; 

 

    // Message content 

    @NotBlank(message = "Message cannot be empty") 

    @Size(max = 500, 

        message = "Message max 500 characters") 

    @Column(nullable = false, 

            columnDefinition = "TEXT") 

    private String content; 

 

    // Chat room identifier 

    @NotBlank(message = "Room ID is required") 

    @Column(name = "room_id", nullable = false) 

    private String roomId; 

 

    // Message type (CHAT/JOIN/LEAVE) 

    @Enumerated(EnumType.STRING) 

    @Column(nullable = false) 

    private MessageType type; 

 

    @Column(name = "timestamp", 

            updatable = false) 

    private LocalDateTime timestamp; 

 

    @PrePersist 

    public void prePersist() { 

        this.timestamp = LocalDateTime.now(); 

    } 

} 
