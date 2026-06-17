/** 

 * AnswerServiceImpl 

 * Handles all answer operations 

 * DESIGN PATTERN: Singleton + Observer 

 */ 

package com.doconnect.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;

import com.doconnect.service.interfaces.AnswerService;

import com.doconnect.repository.AnswerRepository;
import com.doconnect.repository.QuestionRepository;
import com.doconnect.repository.UserRepository;

import com.doconnect.entity.Answer;
import com.doconnect.entity.Question;
import com.doconnect.entity.User;

import com.doconnect.dto.answer.AnswerRequestDTO;
import com.doconnect.dto.answer.AnswerResponseDTO;

import com.doconnect.exception.ResourceNotFoundException;

import com.doconnect.design_patterns.observer.EventPublisher;

@Service 

@RequiredArgsConstructor 

@Slf4j 

public class AnswerServiceImpl 

                implements AnswerService { 

 

    private final AnswerRepository 

                        answerRepository; 

    private final QuestionRepository 

                        questionRepository; 

    private final UserRepository userRepository; 

    private final EventPublisher eventPublisher; 

 

    @Override 

    public AnswerResponseDTO postAnswer( 

                AnswerRequestDTO dto, 

                String email) { 

 

        User user = userRepository 

                .findByEmail(email) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "User not found")); 

 

        Question question = questionRepository 

                .findById(dto.getQuestionId()) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "Question not found: " 

                        + dto.getQuestionId())); 

 

        Answer answer = Answer.builder() 

                .content(dto.getContent()) 

                .question(question) 

                .user(user) 

                .isAiGenerated(false) 

                .build(); 

 

        Answer saved = answerRepository 

                            .save(answer); 

 

        // Notify via Observer Pattern 

        eventPublisher.publishEvent( 

            "ANSWER_POSTED", 

            Map.of( 

                "questionId", 

                dto.getQuestionId(), 

                "answeredBy", email)); 

 

        log.info( 

            "Answer posted by: {}", email); 

        return mapToDTO(saved); 

    } 

 

    @Override 

    public List<AnswerResponseDTO> 

                getAnswersByQuestion( 

                Long questionId) { 

        return answerRepository 

                .findByQuestionId(questionId) 

                .stream() 

                .map(this::mapToDTO) 

                .collect(Collectors.toList()); 

    } 

 

    @Override 

    public AnswerResponseDTO updateAnswer( 

                Long id, 

                AnswerRequestDTO dto, 

                String email) { 

 

        Answer answer = answerRepository 

                .findById(id) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "Answer not found: " + id)); 

 

        if (!answer.getUser().getEmail() 

                   .equals(email)) { 

            throw new AccessDeniedException( 

                "Not authorized to update!"); 

        } 

 

        answer.setContent(dto.getContent()); 

        return mapToDTO( 

                    answerRepository.save(answer)); 

    } 

 

    @Override 

    public void deleteAnswer( 

                Long id, String email) { 

 

        Answer answer = answerRepository 

                .findById(id) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "Answer not found: " + id)); 

 

        if (!answer.getUser().getEmail() 

                   .equals(email)) { 

            throw new AccessDeniedException( 

                "Not authorized to delete!"); 

        } 

 

        answerRepository.delete(answer); 

        log.info("Answer deleted: {}", id); 

    } 

 

    @Override 

    public AnswerResponseDTO acceptAnswer( 

                Long id, String email) { 

 

        Answer answer = answerRepository 

                .findById(id) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "Answer not found: " + id)); 

 

        // Only question owner can accept 

        if (!answer.getQuestion() 

                   .getUser().getEmail() 

                   .equals(email)) { 

            throw new AccessDeniedException( 

                "Only question owner can accept!"); 

        } 

 

        answer.setAccepted(true); 

        return mapToDTO( 

                    answerRepository.save(answer)); 

    } 

 

    @Override 

    public AnswerResponseDTO voteAnswer( 

                Long id, String type) { 

 

        Answer answer = answerRepository 

                .findById(id) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "Answer not found: " + id)); 

 

        if ("up".equals(type)) { 

            answer.setVotes( 

                answer.getVotes() + 1); 

        } else if ("down".equals(type)) { 

            answer.setVotes( 

                answer.getVotes() - 1); 

        } 

 

        return mapToDTO( 

                    answerRepository.save(answer)); 

    } 

 

    // Map Answer to DTO 

    private AnswerResponseDTO mapToDTO( 

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

                .updatedAt(a.getUpdatedAt()) 

                .build(); 

    } 

}