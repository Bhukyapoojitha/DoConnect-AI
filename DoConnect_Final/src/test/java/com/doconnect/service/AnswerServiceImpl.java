package com.doconnect.service; 

 

import com.doconnect.design_patterns.observer.EventPublisher;

/** 

 * Unit Tests for AnswerServiceImpl 

 * Tests answer CRUD + accept + vote 

 */ 



import com.doconnect.dto.answer.AnswerRequestDTO;
import com.doconnect.dto.answer.AnswerResponseDTO;

import com.doconnect.entity.Answer;
import com.doconnect.entity.Question;
import com.doconnect.enums.Role;
import com.doconnect.entity.User;



import com.doconnect.exception.ResourceNotFoundException;

import com.doconnect.repository.AnswerRepository;
import com.doconnect.repository.QuestionRepository;
import com.doconnect.repository.UserRepository;
import com.doconnect.service.impl.AnswerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) 

class AnswerServiceImplTest { 

 

    @Mock 

    private AnswerRepository answerRepository; 

    @Mock 

    private QuestionRepository 

                        questionRepository; 

    @Mock 

    private UserRepository userRepository; 

    @Mock 

    private EventPublisher eventPublisher; 

 

    @InjectMocks 

    private AnswerServiceImpl answerService; 

 

    private User mockUser; 

    private User questionOwner; 

    private Question mockQuestion; 

    private Answer mockAnswer; 

    private AnswerRequestDTO requestDTO; 

 

    @BeforeEach 

    void setUp() { 

        mockUser = User.builder() 

                .id(1L) 

                .username("answerer") 

                .email("answerer@email.com") 

                .role(Role.USER) 

                .questions(new ArrayList<>()) 

                .answers(new ArrayList<>()) 

                .build(); 

 

        questionOwner = User.builder() 

                .id(2L) 

                .username("questioner") 

                .email("questioner@email.com") 

                .role(Role.USER) 

                .questions(new ArrayList<>()) 

                .answers(new ArrayList<>()) 

                .build(); 

 

        mockQuestion = Question.builder() 

                .id(1L) 

                .title("What is Spring Boot?") 

                .content("Explain Spring Boot") 

                .user(questionOwner) 

                .answers(new ArrayList<>()) 

                .tags(new ArrayList<>()) 

                .build(); 

 

        mockAnswer = Answer.builder() 

                .id(1L) 

                .content( 

                  "Spring Boot is a framework") 

                .question(mockQuestion) 

                .user(mockUser) 

                .votes(0) 

                .isAccepted(false) 

                .isAiGenerated(false) 

                .createdAt(LocalDateTime.now()) 

                .build(); 

 

        requestDTO = new AnswerRequestDTO( 

            "Spring Boot is a framework", 

            1L 

        ); 

    } 

 

    // ──────────────────────────── 

    // POST ANSWER TESTS 

    // ──────────────────────────── 

 

    @Test 

    @DisplayName("Post Answer - Success") 

    void testPostAnswer_Success() { 

 

        // ARRANGE 

        when(userRepository.findByEmail( 

            "answerer@email.com")) 

            .thenReturn(Optional.of(mockUser)); 

 

        when(questionRepository.findById(1L)) 

            .thenReturn( 

                Optional.of(mockQuestion)); 

 

        when(answerRepository.save( 

            any(Answer.class))) 

            .thenReturn(mockAnswer); 

 

        // ACT 

        AnswerResponseDTO result = 

            answerService.postAnswer( 

                requestDTO, 

                "answerer@email.com"); 

 

        // ASSERT 

        assertNotNull(result); 

        assertEquals( 

            "Spring Boot is a framework", 

            result.getContent()); 

        assertFalse(result.isAiGenerated()); 

 

        verify(answerRepository, times(1)) 

            .save(any(Answer.class)); 

 

        // Observer was called 

        verify(eventPublisher, times(1)) 

            .publishEvent( 

                eq("ANSWER_POSTED"), any()); 

    } 

 

    @Test 

    @DisplayName("Post Answer - Question Not Found") 

    void testPostAnswer_QuestionNotFound() { 

 

        // ARRANGE 

        when(userRepository.findByEmail( 

            "answerer@email.com")) 

            .thenReturn(Optional.of(mockUser)); 

 

        when(questionRepository.findById(99L)) 

            .thenReturn(Optional.empty()); 

 

        requestDTO.setQuestionId(99L); 

 

        // ACT + ASSERT 

        assertThrows( 

            ResourceNotFoundException.class, 

            () -> answerService.postAnswer( 

                requestDTO, 

                "answerer@email.com")); 

 

        verify(answerRepository, never()) 

            .save(any(Answer.class)); 

    } 

 

    // ──────────────────────────── 

    // GET ANSWERS TESTS 

    // ──────────────────────────── 

 

    @Test 

    @DisplayName("Get Answers By Question") 

    void testGetAnswersByQuestion() { 

 

        // ARRANGE 

        when(answerRepository 

            .findByQuestionId(1L)) 

            .thenReturn(List.of(mockAnswer)); 

 

        // ACT 

        List<AnswerResponseDTO> result = 

            answerService 

                .getAnswersByQuestion(1L); 

 

        // ASSERT 

        assertNotNull(result); 

        assertEquals(1, result.size()); 

        assertEquals( 

            "Spring Boot is a framework", 

            result.get(0).getContent()); 

    } 

 

    @Test 

    @DisplayName("Get Answers - Empty") 

    void testGetAnswersByQuestion_Empty() { 

 

        // ARRANGE 

        when(answerRepository 

            .findByQuestionId(99L)) 

            .thenReturn( 

                Collections.emptyList()); 

 

        // ACT 

        List<AnswerResponseDTO> result = 

            answerService 

                .getAnswersByQuestion(99L); 

 

        // ASSERT 

        assertTrue(result.isEmpty()); 

    } 

 

    // ──────────────────────────── 

    // DELETE ANSWER TESTS 

    // ──────────────────────────── 

 

    @Test 

    @DisplayName("Delete Answer - Success") 

    void testDeleteAnswer_Success() { 

 

        // ARRANGE 

        when(answerRepository.findById(1L)) 

            .thenReturn(Optional.of(mockAnswer)); 

 

        // ACT 

        assertDoesNotThrow(() -> 

            answerService.deleteAnswer( 

                1L, "answerer@email.com")); 

 

        verify(answerRepository, times(1)) 

            .delete(mockAnswer); 

    } 

 

    @Test 

    @DisplayName("Delete Answer - Not Owner") 

    void testDeleteAnswer_NotOwner() { 

 

        // ARRANGE 

        when(answerRepository.findById(1L)) 

            .thenReturn(Optional.of(mockAnswer)); 

 

        // ACT + ASSERT 

        assertThrows( 

            AccessDeniedException.class, 

            () -> answerService.deleteAnswer( 

                1L, "other@email.com")); 

 

        verify(answerRepository, never()) 

            .delete(any(Answer.class)); 

    } 

 

    // ──────────────────────────── 

    // ACCEPT ANSWER TESTS 

    // ──────────────────────────── 

 

    @Test 

    @DisplayName("Accept Answer - Success") 

    void testAcceptAnswer_Success() { 

 

        // ARRANGE 

        when(answerRepository.findById(1L)) 

            .thenReturn(Optional.of(mockAnswer)); 

 

        when(answerRepository.save( 

            any(Answer.class))) 

            .thenReturn(mockAnswer); 

 

        // ACT - questioner accepts 

        AnswerResponseDTO result = 

            answerService.acceptAnswer( 

                1L, 

                "questioner@email.com"); 

 

        // ASSERT 

        assertTrue(mockAnswer.isAccepted()); 

        verify(answerRepository, times(1)) 

            .save(mockAnswer); 

    } 

 

    @Test 

    @DisplayName("Accept Answer - Not Question Owner") 

    void testAcceptAnswer_NotOwner() { 

 

        // ARRANGE 

        when(answerRepository.findById(1L)) 

            .thenReturn(Optional.of(mockAnswer)); 

 

        // ACT + ASSERT 

        // answerer tries to accept own answer 

        assertThrows( 

            AccessDeniedException.class, 

            () -> answerService.acceptAnswer( 

                1L, "answerer@email.com")); 

    } 

 

    // ──────────────────────────── 

    // VOTE ANSWER TESTS 

    // ──────────────────────────── 

 

    @Test 

    @DisplayName("Vote Answer - Upvote") 

    void testVoteAnswer_Upvote() { 

 

        // ARRANGE 

        when(answerRepository.findById(1L)) 

            .thenReturn(Optional.of(mockAnswer)); 

 

        when(answerRepository.save( 

            any(Answer.class))) 

            .thenReturn(mockAnswer); 

 

        // ACT 

        answerService.voteAnswer(1L, "up"); 

 

        // ASSERT 

        assertEquals(1, mockAnswer.getVotes()); 

    } 

 

    @Test 

    @DisplayName("Vote Answer - Downvote") 

    void testVoteAnswer_Downvote() { 

 

        // ARRANGE 

        mockAnswer.setVotes(3); 

 

        when(answerRepository.findById(1L)) 

            .thenReturn(Optional.of(mockAnswer)); 

 

        when(answerRepository.save( 

            any(Answer.class))) 

            .thenReturn(mockAnswer); 

 

        // ACT 

        answerService.voteAnswer(1L, "down"); 

 

        // ASSERT 

        assertEquals(2, mockAnswer.getVotes()); 

    } 

} 