package com.doconnect.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;


 

/** 

 * RegisterRequestDTO 

 * Validates user registration input 

 * Strong password and username rules 

 */ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

@Builder 

public class RegisterRequestDTO { 

 

    @NotBlank(message = "Username is required") 

    @Size(min = 3, max = 20, 

        message = "Username must be 3-20 chars") 

    @Pattern(regexp = "^[a-zA-Z0-9_]*$", 

        message = "Only letters, numbers, underscore") 

    private String username; 

 

    @NotBlank(message = "Email is required") 

    @Email(message = "Enter a valid email") 

    private String email; 

 

    @NotBlank(message = "Password is required") 

    @Size(min = 6, max = 20, 

        message = "Password must be 6-20 chars") 

    @Pattern( 

        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$", 

        message = "Password needs uppercase, lowercase & number") 

    private String password; 
    
    private String role; 

}