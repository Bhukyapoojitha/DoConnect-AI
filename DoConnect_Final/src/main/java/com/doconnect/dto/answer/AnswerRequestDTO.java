package com.doconnect.dto.answer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/** 

 * AnswerRequestDTO 

 * Validates answer submission 

 */ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

public class AnswerRequestDTO { 

 

    @NotBlank(message = "Answer cannot be empty") 

    @Size(min = 10, 

        message = "Answer min 10 characters") 

    private String content; 

 

    @NotNull(message = "Question ID required") 

    @Positive(message = "Invalid question ID") 

    private Long questionId; 

}