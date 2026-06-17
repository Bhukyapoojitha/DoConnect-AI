/** 

 * UserServiceImpl 

 * Handles all user profile operations 

 * 

 * DESIGN PATTERN: Singleton (Spring managed) 

 */ 
package com.doconnect.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;
import java.util.stream.Collectors;



import com.doconnect.repository.UserRepository;
import com.doconnect.service.interfaces.UserService;
import com.doconnect.repository.QuestionRepository;
import com.doconnect.repository.AnswerRepository;

import com.doconnect.entity.User;
import com.doconnect.enums.Role;
import com.doconnect.entity.Question;
import com.doconnect.entity.Answer;
import com.doconnect.entity.Tag;

import com.doconnect.dto.user.UserProfileDTO;
import com.doconnect.dto.user.UpdateProfileDTO;
import com.doconnect.dto.user.ChangePasswordDTO;

import com.doconnect.dto.question.QuestionResponseDTO;
import com.doconnect.dto.answer.AnswerResponseDTO;

import com.doconnect.exception.ResourceNotFoundException;
import com.doconnect.exception.UserAlreadyExistsException;

@Service 

@RequiredArgsConstructor 

@Slf4j 

public class UserServiceImpl 

                implements UserService { 

 

    private final UserRepository userRepository; 

    private final PasswordEncoder passwordEncoder; 

    private final QuestionRepository 

                        questionRepository; 

    private final AnswerRepository 

                        answerRepository; 

 

    @Override 

    public UserProfileDTO getProfile( 

                            String email) { 

        User user = userRepository 

                .findByEmail(email) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "User not found")); 

        return mapToProfileDTO(user); 

    } 

 

    @Override 

    public UserProfileDTO getUserById(Long id) { 

        User user = userRepository 

                .findById(id) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "User not found: " + id)); 

        return mapToProfileDTO(user); 

    } 

 

    @Override 

    public UserProfileDTO updateProfile( 

                String email, 

                UpdateProfileDTO dto) { 

 

        User user = userRepository 

                .findByEmail(email) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "User not found")); 

 

        // Check username not taken by someone else 

        if (!user.getUsername() 

                 .equals(dto.getUsername()) && 

            userRepository.existsByUsername( 

                            dto.getUsername())) { 

            throw new UserAlreadyExistsException( 

                "Username already taken!"); 

        } 

 

        user.setUsername(dto.getUsername()); 

        user.setBio(dto.getBio()); 

        user.setProfilePicture( 

                    dto.getProfilePicture()); 

 

        userRepository.save(user); 

        log.info("Profile updated: {}", email); 

        return mapToProfileDTO(user); 

    } 

 

    @Override 

    public void changePassword( 

                String email, 

                ChangePasswordDTO dto) { 

 

        User user = userRepository 

                .findByEmail(email) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "User not found")); 

 

        // Verify current password 

        if (!passwordEncoder.matches( 

                dto.getCurrentPassword(), 

                user.getPassword())) { 

            throw new BadCredentialsException( 

                "Current password is incorrect!"); 

        } 

 

        // Verify new passwords match 

        if (!dto.getNewPassword() 

                .equals(dto.getConfirmPassword())) { 

            throw new IllegalArgumentException( 

                "Passwords do not match!"); 

        } 

 

        user.setPassword(passwordEncoder.encode( 

                            dto.getNewPassword())); 

        userRepository.save(user); 

        log.info("Password changed: {}", email); 

    } 

 

    @Override 

    public void deleteAccount(String email) { 

        User user = userRepository 

                .findByEmail(email) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "User not found")); 

        userRepository.delete(user); 

        log.info("Account deleted: {}", email); 

    } 

 

    @Override 

    public List<QuestionResponseDTO> 

                    getUserQuestions(Long userId) { 

        return questionRepository 

                .findByUserId(userId) 

                .stream() 

                .map(this::mapQuestionToDTO) 

                .collect(Collectors.toList()); 

    } 

 

    @Override 

    public List<AnswerResponseDTO> 

                    getUserAnswers(Long userId) { 

        return answerRepository 

                .findByUserId(userId) 

                .stream() 

                .map(this::mapAnswerToDTO) 

                .collect(Collectors.toList()); 

    } 

 

    @Override 

    public List<UserProfileDTO> getAllUsers() { 

        return userRepository.findAll() 

                .stream() 

                .map(this::mapToProfileDTO) 

                .collect(Collectors.toList()); 

    } 

 

    @Override 

    public void updateUserRole( 

                Long userId, Role role) { 

        User user = userRepository 

                .findById(userId) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "User not found: " + userId)); 

        user.setRole(role); 

        userRepository.save(user); 

        log.info("Role updated for user: {}", 

                    userId); 

    } 

 

    @Override 

    public void deleteUser(Long userId) { 

        if (!userRepository.existsById(userId)) { 

            throw new ResourceNotFoundException( 

                "User not found: " + userId); 

        } 

        userRepository.deleteById(userId); 

        log.info("User deleted: {}", userId); 

    } 

 

    // Map User to ProfileDTO 

    private UserProfileDTO mapToProfileDTO( 

                                User user) { 

        return UserProfileDTO.builder() 

                .id(user.getId()) 

                .username(user.getUsername()) 

                .email(user.getEmail()) 

                .bio(user.getBio()) 

                .profilePicture( 

                    user.getProfilePicture()) 

                .role(user.getRole().name()) 

                .totalQuestions( 

                    user.getQuestions().size()) 

                .totalAnswers( 

                    user.getAnswers().size()) 

                .createdAt(user.getCreatedAt()) 

                .build(); 

    } 

 

    private QuestionResponseDTO 

                mapQuestionToDTO(Question q) { 

        return QuestionResponseDTO.builder() 

                .id(q.getId()) 

                .title(q.getTitle()) 

                .content(q.getContent()) 

                .username(q.getUser().getUsername()) 

                .userId(q.getUser().getId()) 

                .votes(q.getVotes()) 

                .views(q.getViews()) 

                .tags(q.getTags().stream() 

                    .map(Tag::getName) 

                    .collect(Collectors.toList())) 

                .createdAt(q.getCreatedAt()) 

                .build(); 

    } 

 

    private AnswerResponseDTO mapAnswerToDTO( 

                                    Answer a) { 

        return AnswerResponseDTO.builder() 

                .id(a.getId()) 

                .content(a.getContent()) 

                .username(a.getUser().getUsername()) 

                .userId(a.getUser().getId()) 

                .questionId( 

                    a.getQuestion().getId()) 

                .votes(a.getVotes()) 

                .isAccepted(a.isAccepted()) 

                .isAiGenerated(a.isAiGenerated()) 

                .createdAt(a.getCreatedAt()) 

                .build(); 

    } 

} 