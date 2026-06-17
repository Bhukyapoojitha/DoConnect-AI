package com.doconnect.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;


/** 

 * AuthResponseDTO 

 * Returns JWT token after login/register 

 */ 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

@Builder 

public class AuthResponseDTO { 

    private String token; 

    private String username; 

    private String email; 

    private String role; 

} 