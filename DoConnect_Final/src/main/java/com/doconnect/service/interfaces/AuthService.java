package com.doconnect.service.interfaces;

import com.doconnect.dto.auth.AuthResponseDTO;
import com.doconnect.dto.auth.LoginRequestDTO;
import com.doconnect.dto.auth.RegisterRequestDTO;

public interface AuthService { 

    AuthResponseDTO register( 

            RegisterRequestDTO request); 

    AuthResponseDTO login( 

            LoginRequestDTO request); 

} 