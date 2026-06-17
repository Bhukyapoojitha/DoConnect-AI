package com.doconnect.service;

import com.doconnect.dto.user.ChangePasswordDTO;
import com.doconnect.dto.user.UpdateProfileDTO;
import com.doconnect.dto.user.UserProfileDTO;
import com.doconnect.enums.Role;
import com.doconnect.entity.User;
import com.doconnect.exception.ResourceNotFoundException;
import com.doconnect.exception.UserAlreadyExistsException;
import com.doconnect.repository.AnswerRepository;
import com.doconnect.repository.QuestionRepository;
import com.doconnect.repository.UserRepository;
import com.doconnect.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;
    private UpdateProfileDTO updateDTO;
    private ChangePasswordDTO passwordDTO;

    @BeforeEach
    void setUp() {

        mockUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@email.com")
                .password("encodedOldPassword")
                .bio("I am a developer")
                .role(Role.USER)
                .questions(new ArrayList<>())
                .answers(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .build();

        updateDTO = new UpdateProfileDTO(
                "newusername",
                "Updated bio",
                null
        );

        passwordDTO = new ChangePasswordDTO(
                "OldPass@1",
                "NewPass@1",
                "NewPass@1"
        );
    }

    // ================= GET PROFILE =================

    @Test
    @DisplayName("Get Profile - Success")
    void testGetProfile_Success() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(mockUser));

        UserProfileDTO result =
                userService.getProfile("test@email.com");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@email.com", result.getEmail());
        assertEquals("USER", result.getRole());

        verify(userRepository, times(1))
                .findByEmail("test@email.com");
    }

    @Test
    @DisplayName("Get Profile - User Not Found")
    void testGetProfile_NotFound() {

        when(userRepository.findByEmail("notfound@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getProfile("notfound@email.com")
        );
    }

    // ================= UPDATE PROFILE =================

    @Test
    @DisplayName("Update Profile - Success")
    void testUpdateProfile_Success() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(mockUser));

        when(userRepository.existsByUsername("newusername"))
                .thenReturn(false);

        when(userRepository.save(any(User.class)))
                .thenReturn(mockUser);

        UserProfileDTO result =
                userService.updateProfile("test@email.com", updateDTO);

        assertNotNull(result);

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    @DisplayName("Update Profile - Username Taken")
    void testUpdateProfile_UsernameTaken() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(mockUser));

        when(userRepository.existsByUsername("newusername"))
                .thenReturn(true);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.updateProfile("test@email.com", updateDTO)
        );

        verify(userRepository, never())
                .save(any(User.class));
    }

    // ================= CHANGE PASSWORD =================

    @Test
    @DisplayName("Change Password - Success")
    void testChangePassword_Success() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(mockUser));

        when(passwordEncoder.matches("OldPass@1", "encodedOldPassword"))
                .thenReturn(true);

        when(passwordEncoder.encode("NewPass@1"))
                .thenReturn("encodedNewPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(mockUser);

        assertDoesNotThrow(() ->
                userService.changePassword("test@email.com", passwordDTO)
        );

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    @DisplayName("Change Password - Wrong Current Password")
    void testChangePassword_WrongCurrent() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(mockUser));

        when(passwordEncoder.matches("OldPass@1", "encodedOldPassword"))
                .thenReturn(false);

        assertThrows(
                BadCredentialsException.class,
                () -> userService.changePassword("test@email.com", passwordDTO)
        );

        verify(userRepository, never())
                .save(any(User.class));
    }

    @Test
    @DisplayName("Change Password - Password Mismatch")
    void testChangePassword_Mismatch() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(mockUser));

        when(passwordEncoder.matches("OldPass@1", "encodedOldPassword"))
                .thenReturn(true);

        passwordDTO.setConfirmPassword("Different@1");

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.changePassword("test@email.com", passwordDTO)
        );
    }

    // ================= DELETE ACCOUNT =================

    @Test
    @DisplayName("Delete Account - Success")
    void testDeleteAccount_Success() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(mockUser));

        assertDoesNotThrow(() ->
                userService.deleteAccount("test@email.com")
        );

        verify(userRepository, times(1))
                .delete(mockUser);
    }

    @Test
    @DisplayName("Delete Account - Not Found")
    void testDeleteAccount_NotFound() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.deleteAccount("test@email.com")
        );

        verify(userRepository, never())
                .delete(any(User.class));
    }
}