package com.doconnect.service.interfaces;

import java.util.List;

import com.doconnect.dto.answer.AnswerRequestDTO;
import com.doconnect.dto.answer.AnswerResponseDTO;

public interface AnswerService { 

    AnswerResponseDTO postAnswer( 

            AnswerRequestDTO dto, String email); 

    List<AnswerResponseDTO> getAnswersByQuestion( 

            Long questionId); 

    AnswerResponseDTO updateAnswer( 

            Long id, 

            AnswerRequestDTO dto, 

            String email); 

    void deleteAnswer(Long id, String email); 

    AnswerResponseDTO acceptAnswer( 

            Long id, String email); 

    AnswerResponseDTO voteAnswer( 

            Long id, String type); 

}
