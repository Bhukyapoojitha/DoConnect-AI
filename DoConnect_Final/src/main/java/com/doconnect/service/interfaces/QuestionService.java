package com.doconnect.service.interfaces;

import java.util.List;

import com.doconnect.dto.question.QuestionRequestDTO;
import com.doconnect.dto.question.QuestionResponseDTO;

public interface QuestionService { 

    QuestionResponseDTO createQuestion( 

            QuestionRequestDTO dto, 

            String email); 

    List<QuestionResponseDTO> getAllQuestions(); 

    QuestionResponseDTO getQuestionById(Long id); 

    QuestionResponseDTO updateQuestion( 

            Long id, 

            QuestionRequestDTO dto, 

            String email); 

    void deleteQuestion(Long id, String email); 

    List<QuestionResponseDTO> searchQuestions( 

            String keyword); 

    List<QuestionResponseDTO> getTrendingQuestions(); 

    List<QuestionResponseDTO> getQuestionsByTag( 

            String tagName); 

    QuestionResponseDTO voteQuestion( 

            Long id, String type); 

} 