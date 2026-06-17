package com.doconnect.dto.answer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;




/** 

* AnswerResponseDTO 

* Returns answer data to frontend 

*/ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

@Builder 

public class AnswerResponseDTO { 

   private Long id; 

   private String content; 

   private String username; 

   private Long userId; 

   private Long questionId; 

   private int votes; 

   private boolean isAccepted; 

   private boolean isAiGenerated; 

   private LocalDateTime createdAt; 

   private LocalDateTime updatedAt; 

} 
