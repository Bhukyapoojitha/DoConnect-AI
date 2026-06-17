package com.doconnect.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;




/** 

* UserProfileDTO 

* Returns user profile data 

*/ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

@Builder 

public class UserProfileDTO { 

   private Long id; 

   private String username; 

   private String email; 

   private String bio; 

   private String profilePicture; 

   private String role; 

   private int totalQuestions; 

   private int totalAnswers; 

   private LocalDateTime createdAt; 

} 