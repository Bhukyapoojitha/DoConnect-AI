package com.doconnect.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;




/** 

* ChangePasswordDTO 

* Validates password change request 

*/ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

public class ChangePasswordDTO { 



   @NotBlank( 

       message = "Current password is required") 

   private String currentPassword; 



   @NotBlank( 

       message = "New password is required") 

   @Size(min = 6, max = 20, 

       message = "Password must be 6-20 chars") 

   @Pattern( 

       regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$", 

       message = "Need uppercase, lowercase & number") 

   private String newPassword; 



   @NotBlank( 

       message = "Confirm password is required") 

   private String confirmPassword; 

}