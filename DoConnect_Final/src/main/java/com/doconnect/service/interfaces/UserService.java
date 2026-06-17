package com.doconnect.service.interfaces;

import java.util.List;

import com.doconnect.dto.answer.AnswerResponseDTO;
import com.doconnect.dto.question.QuestionResponseDTO;
import com.doconnect.dto.user.ChangePasswordDTO;
import com.doconnect.dto.user.UpdateProfileDTO;
import com.doconnect.dto.user.UserProfileDTO;
import com.doconnect.enums.Role;

public interface UserService { 

    UserProfileDTO getProfile(String email); 

    UserProfileDTO getUserById(Long id); 

    UserProfileDTO updateProfile( 

            String email, 

            UpdateProfileDTO dto); 

    void changePassword( 

            String email, 

            ChangePasswordDTO dto); 

    void deleteAccount(String email); 

    List<QuestionResponseDTO> getUserQuestions( 

            Long userId); 

    List<AnswerResponseDTO> getUserAnswers( 

            Long userId); 

    List<UserProfileDTO> getAllUsers(); 

    void updateUserRole(Long userId, Role role); 

    void deleteUser(Long userId); 

}
