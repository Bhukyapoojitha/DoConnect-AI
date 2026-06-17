/** 

 * QuestionServiceImpl 

 * Handles all question operations 

 * Auto-triggers AI answer generation 

 * DESIGN PATTERN: Singleton + Observer 

 */ 

package com.doconnect.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;

import com.doconnect.service.interfaces.QuestionService;
import com.doconnect.service.interfaces.AIService;

import com.doconnect.repository.QuestionRepository;
import com.doconnect.repository.UserRepository;
import com.doconnect.repository.TagRepository;
import com.doconnect.repository.AnswerRepository;

import com.doconnect.entity.Question;
import com.doconnect.entity.User;
import com.doconnect.entity.Tag;
import com.doconnect.entity.Answer;

import com.doconnect.dto.question.QuestionRequestDTO;
import com.doconnect.dto.question.QuestionResponseDTO;

import com.doconnect.exception.ResourceNotFoundException;

import com.doconnect.design_patterns.observer.EventPublisher;

@Service 

@RequiredArgsConstructor 

@Slf4j 

public class QuestionServiceImpl 

                implements QuestionService { 

 

    private final QuestionRepository 

                        questionRepository; 

    private final UserRepository userRepository; 

    private final TagRepository tagRepository; 

    private final AnswerRepository 

                        answerRepository; 

    private final AIService aiService; 

    private final EventPublisher eventPublisher; 

 

    @Override 

    public QuestionResponseDTO createQuestion( 

                QuestionRequestDTO dto, 

                String email) { 

 

        log.info( 

            "Creating question for: {}", email); 

 

        User user = userRepository 

                .findByEmail(email) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "User not found")); 

 

        // Check content moderation 

        if (aiService.isToxicContent( 

                        dto.getContent())) { 

            throw new IllegalArgumentException( 

                "Content violates guidelines!"); 

        } 

 

        // Handle tags - create if not exists 

        List<Tag> tags = dto.getTags().stream() 

                .map(tagName -> tagRepository 

                    .findByName(tagName) 

                    .orElseGet(() -> { 

                        Tag t = new Tag(); 

                        t.setName(tagName 

                            .toLowerCase().trim()); 

                        return tagRepository 

                                        .save(t); 

                    })) 

                .collect(Collectors.toList()); 

 

        // Build and save question 

        Question question = Question.builder() 

                .title(dto.getTitle()) 

                .content(dto.getContent()) 

                .user(user) 

                .tags(tags) 

                .build(); 

 

        Question saved = questionRepository 

                            .save(question); 

 

        // Auto-generate AI answer async 

        CompletableFuture.runAsync(() -> { 

            try { 

                String aiAnswer = 

                    aiService.generateAnswer( 

                        saved.getTitle(), 

                        saved.getContent()); 

 

                Answer answer = Answer.builder() 

                        .content(aiAnswer) 

                        .question(saved) 

                        .user(user) 

                        .isAiGenerated(true) 

                        .build(); 

 

                answerRepository.save(answer); 

                log.info( 

                    "AI answer saved for: {}", 

                    saved.getId()); 

            } catch (Exception e) { 

                log.error( 

                    "AI answer failed: {}", 

                    e.getMessage()); 

            } 

        }); 

 

        // Notify observers 

        eventPublisher.publishEvent( 

            "QUESTION_CREATED", 

            Map.of("questionId", saved.getId(), 

                   "by", email)); 

 

        return mapToResponseDTO(saved); 

    } 

 

    @Override 

    public List<QuestionResponseDTO> 

                        getAllQuestions() { 

        return questionRepository.findAll() 

                .stream() 

                .map(this::mapToResponseDTO) 

                .collect(Collectors.toList()); 

    } 

 

    @Override 

    public QuestionResponseDTO getQuestionById( 

                                    Long id) { 

        Question q = questionRepository 

                .findById(id) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "Question not found: " + id)); 

 

        // Increment view count 

        q.setViews(q.getViews() + 1); 

        questionRepository.save(q); 

        return mapToResponseDTO(q); 

    } 

 

    @Override 

    public QuestionResponseDTO updateQuestion( 

                Long id, 

                QuestionRequestDTO dto, 

                String email) { 

 

        Question q = questionRepository 

                .findById(id) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "Question not found: " + id)); 

 

        // Only owner can update 

        if (!q.getUser().getEmail() 

               .equals(email)) { 

            throw new AccessDeniedException( 

                "Not authorized to update!"); 

        } 

 

        q.setTitle(dto.getTitle()); 

        q.setContent(dto.getContent()); 

        questionRepository.save(q); 

        return mapToResponseDTO(q); 

    } 

 

    @Override 

    public void deleteQuestion( 

                Long id, String email) { 

 

        Question q = questionRepository 

                .findById(id) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "Question not found: " + id)); 

 

        // Only owner can delete 

        if (!q.getUser().getEmail() 

               .equals(email)) { 

            throw new AccessDeniedException( 

                "Not authorized to delete!"); 

        } 

 

        questionRepository.delete(q); 

        log.info("Question deleted: {}", id); 

    } 

 

    @Override 

    public List<QuestionResponseDTO> 

                    searchQuestions( 

                    String keyword) { 

        return questionRepository 

                .findByTitleContaining(keyword) 

                .stream() 

                .map(this::mapToResponseDTO) 

                .collect(Collectors.toList()); 

    } 

 

    @Override 

    public List<QuestionResponseDTO> 

                    getTrendingQuestions() { 

        return questionRepository 

                .findTop10ByOrderByViewsDesc() 

                .stream() 

                .map(this::mapToResponseDTO) 

                .collect(Collectors.toList()); 

    } 

 

    @Override 

    public List<QuestionResponseDTO> 

                    getQuestionsByTag( 

                    String tagName) { 

        return questionRepository 

                .findByTagName(tagName) 

                .stream() 

                .map(this::mapToResponseDTO) 

                .collect(Collectors.toList()); 

    } 

 

    @Override 

    public QuestionResponseDTO voteQuestion( 

                Long id, String type) { 

        Question q = questionRepository 

                .findById(id) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "Question not found: " + id)); 

 

        if ("up".equals(type)) { 

            q.setVotes(q.getVotes() + 1); 

        } else if ("down".equals(type)) { 

            q.setVotes(q.getVotes() - 1); 

        } 

 

        return mapToResponseDTO( 

                    questionRepository.save(q)); 

    } 

 

    // Map Question to ResponseDTO 

    private QuestionResponseDTO mapToResponseDTO( 

                                    Question q) { 

        return QuestionResponseDTO.builder() 

                .id(q.getId()) 

                .title(q.getTitle()) 

                .content(q.getContent()) 

                .username(q.getUser().getUsername()) 

                .userId(q.getUser().getId()) 

                .votes(q.getVotes()) 

                .views(q.getViews()) 

                .answerCount( 

                    q.getAnswers().size()) 

                .tags(q.getTags().stream() 

                    .map(Tag::getName) 

                    .collect(Collectors.toList())) 

                .createdAt(q.getCreatedAt()) 

                .updatedAt(q.getUpdatedAt()) 

                .build(); 

    } 

} 