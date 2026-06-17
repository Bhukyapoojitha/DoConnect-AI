package com.doconnect.dto.chat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import com.doconnect.enums.MessageType;




/** 

* ChatMessageDTO 

* WebSocket message transfer object 

*/ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

@Builder 

public class ChatMessageDTO { 



   @NotBlank(message = "Sender is required") 

   private String sender; 



   @NotBlank(message = "Message cannot be empty") 

   @Size(max = 500, 

       message = "Message max 500 characters") 

   private String content; 



   @NotBlank(message = "Room ID is required") 

   private String roomId; 



   private MessageType type; 

   private LocalDateTime timestamp; 

} 