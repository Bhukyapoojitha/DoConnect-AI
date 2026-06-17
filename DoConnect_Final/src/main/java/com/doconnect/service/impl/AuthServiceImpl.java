package com.doconnect.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.doconnect.repository.UserRepository;

import com.doconnect.dto.auth.RegisterRequestDTO;
import com.doconnect.entity.User;
import com.doconnect.enums.Role;
import com.doconnect.dto.auth.LoginRequestDTO;
import com.doconnect.dto.auth.AuthResponseDTO;

import com.doconnect.exception.UserAlreadyExistsException;
import com.doconnect.exception.ResourceNotFoundException;

import com.doconnect.security.JwtUtil;
import com.doconnect.service.interfaces.AuthService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {

        log.info("Registering user: {}", request.getEmail());

        // Check email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered!");
        }

        // Check username
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already taken!");
        }

        // Role handling
        Role userRole = Role.USER;
        if (request.getRole() != null) {
            try {
                userRole = Role.valueOf(request.getRole().toUpperCase());
            } catch (Exception e) {
                userRole = Role.USER;
            }
        }

        // Create user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return AuthResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {

        log.info("Login attempt: {}", request.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        String token = jwtUtil.generateToken(user.getEmail());

        log.info("Login successful: {}", user.getEmail());

        return AuthResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}