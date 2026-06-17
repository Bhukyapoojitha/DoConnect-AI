package com.doconnect.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;




/** 

* LoginRequestDTO 

* Validates login credentials 

*/ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

public class LoginRequestDTO { 



   @NotBlank(message = "Email is required") 

   @Email(message = "Enter a valid email") 

   private String email; 



   @NotBlank(message = "Password is required") 

   @Size(min = 6, 

       message = "Password min 6 characters") 

   private String password; 

} 