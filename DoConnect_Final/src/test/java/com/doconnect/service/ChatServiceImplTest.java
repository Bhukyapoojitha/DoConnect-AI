package com.doconnect.service; 

 

/** 

 * Unit Tests for ChatServiceImpl 

 * Tests chat message save and history 

 */ 

import com.doconnect.dto.chat.ChatMessageDTO;
import com.doconnect.entity.ChatMessage;
import com.doconnect.enums.MessageType;

import com.doconnect.repository.ChatMessageRepository;
import com.doconnect.service.impl.ChatServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class) 

class ChatServiceImplTest { 

 

    @Mock 

    private ChatMessageRepository 

                        chatMessageRepository; 

 

    @InjectMocks 

    private ChatServiceImpl chatService; 

 

    private ChatMessage mockMessage; 

    private ChatMessageDTO messageDTO; 

 

    @BeforeEach 

    void setUp() { 

        mockMessage = ChatMessage.builder() 

                .id(1L) 

                .sender("testuser") 

                .content("Hello everyone!") 

                .roomId("general") 

                .type(MessageType.CHAT) 

                .timestamp(LocalDateTime.now()) 

                .build(); 

 

        messageDTO = ChatMessageDTO.builder() 

                .sender("testuser") 

                .content("Hello everyone!") 

                .roomId("general") 

                .type(MessageType.CHAT) 

                .build(); 

    } 

 

    // ──────────────────────────── 

    // SAVE MESSAGE TESTS 

    // ──────────────────────────── 

 

    @Test 

    @DisplayName("Save Message - Success") 

    void testSaveMessage_Success() { 

 

        // ARRANGE 

        when(chatMessageRepository.save( 

            any(ChatMessage.class))) 

            .thenReturn(mockMessage); 

 

        // ACT 

        ChatMessageDTO result = 

            chatService.saveMessage(messageDTO); 

 

        // ASSERT 

        assertNotNull(result); 

        assertEquals("testuser", 

                        result.getSender()); 

        assertEquals("Hello everyone!", 

                        result.getContent()); 

        assertEquals("general", 

                        result.getRoomId()); 

        assertNotNull(result.getTimestamp()); 

 

        verify(chatMessageRepository, times(1)) 

            .save(any(ChatMessage.class)); 

    } 

 

    @Test 

    @DisplayName("Save Message - Timestamp Set") 

    void testSaveMessage_TimestampSet() { 

 

        // ARRANGE 

        when(chatMessageRepository.save( 

            any(ChatMessage.class))) 

            .thenReturn(mockMessage); 

 

        // ACT 

        ChatMessageDTO result = 

            chatService.saveMessage(messageDTO); 

 

        // ASSERT 

        // Timestamp should be set from saved msg 

        assertNotNull(result.getTimestamp()); 

        assertEquals( 

            mockMessage.getTimestamp(), 

            result.getTimestamp()); 

    } 

 

    @Test 

    @DisplayName("Save JOIN Message") 

    void testSaveMessage_JoinType() { 

 

        // ARRANGE 

        messageDTO.setType(MessageType.JOIN); 

        messageDTO.setContent( 

            "testuser joined the room"); 

 

        ChatMessage joinMessage = 

            ChatMessage.builder() 

                .id(2L) 

                .sender("testuser") 

                .content("testuser joined the room") 

                .roomId("general") 

                .type(MessageType.JOIN) 

                .timestamp(LocalDateTime.now()) 

                .build(); 

 

        when(chatMessageRepository.save( 

            any(ChatMessage.class))) 

            .thenReturn(joinMessage); 

 

        // ACT 

        ChatMessageDTO result = 

            chatService.saveMessage(messageDTO); 

 

        // ASSERT 

        assertEquals(MessageType.JOIN, 

                        result.getType()); 

    } 

 

    // ──────────────────────────── 

    // GET HISTORY TESTS 

    // ──────────────────────────── 

 

    @Test 

    @DisplayName("Get Room History - Success") 

    void testGetRoomHistory_Success() { 

 

        // ARRANGE 

        when(chatMessageRepository 

            .findByRoomIdOrderByTimestampAsc( 

                "general")) 

            .thenReturn(List.of(mockMessage)); 

 

        // ACT 

        List<ChatMessageDTO> result = 

            chatService.getRoomHistory("general"); 

 

        // ASSERT 

        assertNotNull(result); 

        assertEquals(1, result.size()); 

        assertEquals("general", 

                result.get(0).getRoomId()); 

        assertEquals("testuser", 

                result.get(0).getSender()); 

    } 

 

    @Test 

    @DisplayName("Get Room History - Empty Room") 

    void testGetRoomHistory_Empty() { 

 

        // ARRANGE 

        when(chatMessageRepository 

            .findByRoomIdOrderByTimestampAsc( 

                "emptyroom")) 

            .thenReturn( 

                Collections.emptyList()); 

 

        // ACT 

        List<ChatMessageDTO> result = 

            chatService 

                .getRoomHistory("emptyroom"); 

 

        // ASSERT 

        assertNotNull(result); 

        assertTrue(result.isEmpty()); 

    } 

 

    @Test 

    @DisplayName("Get History - Multiple Messages") 

    void testGetRoomHistory_Multiple() { 

 

        // ARRANGE 

        ChatMessage msg2 = ChatMessage.builder() 

                .id(2L) 

                .sender("user2") 

                .content("Hi there!") 

                .roomId("general") 

                .type(MessageType.CHAT) 

                .timestamp(LocalDateTime.now() 

                    .plusMinutes(1)) 

                .build(); 

 

        when(chatMessageRepository 

            .findByRoomIdOrderByTimestampAsc( 

                "general")) 

            .thenReturn( 

                List.of(mockMessage, msg2)); 

 

        // ACT 

        List<ChatMessageDTO> result = 

            chatService.getRoomHistory("general"); 

 

        // ASSERT 

        assertEquals(2, result.size()); 

        // First message should be earlier 

        assertEquals("testuser", 

                result.get(0).getSender()); 

        assertEquals("user2", 

                result.get(1).getSender()); 

    } 

} 