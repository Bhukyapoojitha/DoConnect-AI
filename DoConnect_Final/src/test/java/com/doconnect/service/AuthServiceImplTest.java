package com.doconnect.service;

import com.doconnect.dto.auth.AuthResponseDTO;
import com.doconnect.dto.auth.LoginRequestDTO;
import com.doconnect.dto.auth.RegisterRequestDTO;
import com.doconnect.enums.Role;
import com.doconnect.entity.User;
import com.doconnect.exception.ResourceNotFoundException;
import com.doconnect.exception.UserAlreadyExistsException;
import com.doconnect.repository.UserRepository;
import com.doconnect.security.JwtUtil;
import com.doconnect.service.impl.AuthServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    // ── Mock all dependencies ──
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    // ── Inject mocks into service ──
    @InjectMocks
    private AuthServiceImpl authService;

    // ── Test data ──
    private RegisterRequestDTO registerRequest;
    private LoginRequestDTO loginRequest;
    private User mockUser;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequestDTO(
                "testuser",
                "test@email.com",
                "Test@123",
                "User"
        );

        loginRequest = new LoginRequestDTO(
                "test@email.com",
                "Test@123"
        );

        mockUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@email.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
    }
    
    

    // ================= REGISTER TESTS =================

    @Test
    @DisplayName("Register - Success")
    void testRegister_Success() {

        when(userRepository.existsByEmail("test@email.com"))
                .thenReturn(false);

        when(userRepository.existsByUsername("testuser"))
                .thenReturn(false);

        when(passwordEncoder.encode("Test@123"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(mockUser);

        when(jwtUtil.generateToken("test@email.com"))
                .thenReturn("mock.jwt.token");

        AuthResponseDTO result = authService.register(registerRequest);

        assertNotNull(result);
        assertEquals("mock.jwt.token", result.getToken());
        assertEquals("testuser", result.getUsername());
        assertEquals("USER", result.getRole());

        verify(userRepository, times(1))
                .existsByEmail("test@email.com");

        verify(userRepository, times(1))
                .save(any(User.class));

        verify(passwordEncoder, times(1))
                .encode("Test@123");
    }

    @Test
    @DisplayName("Register - Email Already Exists")
    void testRegister_EmailExists() {

        when(userRepository.existsByEmail("test@email.com"))
                .thenReturn(true);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> authService.register(registerRequest)
        );

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Register - Username Already Taken")
    void testRegister_UsernameExists() {

        when(userRepository.existsByEmail("test@email.com"))
                .thenReturn(false);

        when(userRepository.existsByUsername("testuser"))
                .thenReturn(true);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> authService.register(registerRequest)
        );

        verify(userRepository, never()).save(any(User.class));
    }

    // ================= LOGIN TESTS =================

    @Test
    @DisplayName("Login - Success")
    void testLogin_Success() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(mockUser));

        when(jwtUtil.generateToken("test@email.com"))
                .thenReturn("mock.jwt.token");

        AuthResponseDTO result = authService.login(loginRequest);

        assertNotNull(result);
        assertEquals("mock.jwt.token", result.getToken());
        assertEquals("testuser", result.getUsername());
        assertEquals("USER", result.getRole());
    }

    @Test
    @DisplayName("Login - User Not Found")
    void testLogin_UserNotFound() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> authService.login(loginRequest)
        );
    }

    @Test
    @DisplayName("Login - Token Generated Correctly")
    void testLogin_TokenGenerated() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(mockUser));

        when(jwtUtil.generateToken("test@email.com"))
                .thenReturn("valid.jwt.token");

        AuthResponseDTO result = authService.login(loginRequest);

        assertNotNull(result.getToken());
        assertTrue(result.getToken().length() > 0);

        verify(jwtUtil, times(1))
                .generateToken("test@email.com");
    }
}