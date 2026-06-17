package com.doconnect.dto.question;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.time.LocalDateTime;

/** 

 * QuestionResponseDTO 

 * Returns question data to frontend 

 */ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

@Builder 

public class QuestionResponseDTO { 

    private Long id; 

    private String title; 

    private String content; 

    private String username; 

    private Long userId; 

    private int votes; 

    private int views; 

    private int answerCount; 

    private List<String> tags; 

    private LocalDateTime createdAt; 

    private LocalDateTime updatedAt; 

} 
