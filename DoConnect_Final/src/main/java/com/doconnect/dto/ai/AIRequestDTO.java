package com.doconnect.dto.ai;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
/** 

 * AIRequestDTO 

 * Request body for AI operations 

 */ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

public class AIRequestDTO { 

 

    @NotBlank( 

        message = "Question title is required") 

    private String questionTitle; 

 

    @NotBlank( 

        message = "Question content is required") 

    private String questionContent; 

 

    private List<String> existingTags; 

} 