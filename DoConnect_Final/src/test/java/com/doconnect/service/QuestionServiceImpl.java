package com.doconnect.service;

import com.doconnect.dto.question.QuestionRequestDTO;
import com.doconnect.dto.question.QuestionResponseDTO;
import com.doconnect.entity.Question;
import com.doconnect.enums.Role;
import com.doconnect.entity.Tag;
import com.doconnect.entity.User;
import com.doconnect.design_patterns.observer.EventPublisher;
import com.doconnect.exception.ResourceNotFoundException;
import com.doconnect.repository.AnswerRepository;
import com.doconnect.repository.QuestionRepository;
import com.doconnect.repository.TagRepository;
import com.doconnect.repository.UserRepository;
import com.doconnect.service.impl.QuestionServiceImpl;
import com.doconnect.service.interfaces.AIService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private AIService aiService;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private QuestionServiceImpl questionService;

    private User mockUser;
    private Question mockQuestion;
    private Tag mockTag;
    private QuestionRequestDTO requestDTO;

    @BeforeEach
    void setUp() {

        mockUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@email.com")
                .role(Role.USER)
                .questions(new ArrayList<>())
                .answers(new ArrayList<>())
                .build();

        mockTag = Tag.builder()
                .id(1L)
                .name("java")
                .questions(new ArrayList<>())
                .build();

        mockQuestion = Question.builder()
                .id(1L)
                .title("What is Spring Boot?")
                .content("Can someone explain Spring Boot?")
                .user(mockUser)
                .tags(List.of(mockTag))
                .votes(0)
                .views(0)
                .answers(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .build();

        requestDTO = new QuestionRequestDTO(
                "What is Spring Boot?",
                "Can someone explain Spring Boot?",
                List.of("java")
        );
    }

    // ================= CREATE =================

    @Test
    @DisplayName("Create Question - Success")
    void testCreateQuestion_Success() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(mockUser));

        when(aiService.isToxicContent(anyString()))
                .thenReturn(false);

        when(tagRepository.findByName("java"))
                .thenReturn(Optional.of(mockTag));

        when(questionRepository.save(any(Question.class)))
                .thenReturn(mockQuestion);

        QuestionResponseDTO result =
                questionService.createQuestion(requestDTO, "test@email.com");

        assertNotNull(result);
        assertEquals("What is Spring Boot?", result.getTitle());
        assertEquals("testuser", result.getUsername());
        assertEquals(1, result.getTags().size());

        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    @DisplayName("Create Question - Toxic Content")
    void testCreateQuestion_ToxicContent() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(mockUser));

        when(aiService.isToxicContent(anyString()))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> questionService.createQuestion(requestDTO, "test@email.com")
        );

        verify(questionRepository, never()).save(any(Question.class));
    }

    @Test
    @DisplayName("Create Question - User Not Found")
    void testCreateQuestion_UserNotFound() {

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> questionService.createQuestion(requestDTO, "test@email.com")
        );

        verify(questionRepository, never()).save(any(Question.class));
    }

    // ================= GET ALL =================

    @Test
    void testGetAllQuestions_Success() {

        when(questionRepository.findAll())
                .thenReturn(List.of(mockQuestion));

        List<QuestionResponseDTO> result =
                questionService.getAllQuestions();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    // ================= GET BY ID =================

    @Test
    void testGetQuestionById_Success() {

        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(mockQuestion));

        when(questionRepository.save(any()))
                .thenReturn(mockQuestion);

        QuestionResponseDTO result =
                questionService.getQuestionById(1L);

        assertNotNull(result);
        assertEquals(1, mockQuestion.getViews()); // ✅ increment check
    }

    // ================= DELETE =================

    @Test
    void testDeleteQuestion_Success() {

        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(mockQuestion));

        assertDoesNotThrow(() ->
                questionService.deleteQuestion(1L, "test@email.com")
        );

        verify(questionRepository).delete(mockQuestion);
    }

    @Test
    void testDeleteQuestion_NotOwner() {

        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(mockQuestion));

        assertThrows(
                AccessDeniedException.class,
                () -> questionService.deleteQuestion(1L, "other@email.com")
        );
    }

    // ================= SEARCH =================

    @Test
    void testSearchQuestions() {

        when(questionRepository.findByTitleContaining("Spring"))
                .thenReturn(List.of(mockQuestion));

        List<QuestionResponseDTO> result =
                questionService.searchQuestions("Spring");

        assertFalse(result.isEmpty());
    }

    // ================= VOTE =================

    @Test
    void testVoteQuestion_Upvote() {

        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(mockQuestion));

        when(questionRepository.save(any()))
                .thenReturn(mockQuestion);

        questionService.voteQuestion(1L, "up");

        assertEquals(1, mockQuestion.getVotes());
    }

    @Test
    void testVoteQuestion_Downvote() {

        mockQuestion.setVotes(5);

        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(mockQuestion));

        when(questionRepository.save(any()))
                .thenReturn(mockQuestion);

        questionService.voteQuestion(1L, "down");

        assertEquals(4, mockQuestion.getVotes());
    }
}