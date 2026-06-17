package com.doconnect.dto.question;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/** 

 * QuestionRequestDTO 

 * Validates question creation/update input 

 */ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

public class QuestionRequestDTO { 

 

    @NotBlank(message = "Title is required") 

    @Size(min = 10, max = 150, 

        message = "Title must be 10-150 chars") 

    private String title; 

 

    @NotBlank(message = "Content is required") 

    @Size(min = 20, 

        message = "Content min 20 characters") 

    private String content; 

 

    @NotNull(message = "Tags cannot be null") 

    @Size(min = 1, max = 5, 

        message = "Add 1 to 5 tags") 

    private List<String> tags; 

}
