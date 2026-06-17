package com.doconnect.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/** 

 * UpdateProfileDTO 

 * Validates profile update request 

 */ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

public class UpdateProfileDTO { 

 

    @NotBlank(message = "Username is required") 

    @Size(min = 3, max = 20, 

        message = "Username must be 3-20 chars") 

    private String username; 

 

    @Size(max = 200, 

        message = "Bio max 200 characters") 

    private String bio; 

 

    private String profilePicture; 

}
