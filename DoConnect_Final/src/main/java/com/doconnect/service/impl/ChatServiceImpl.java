/** 

 * ChatServiceImpl 

 * Handles WebSocket chat operations 

 * Saves and retrieves chat history 

 */ 

package com.doconnect.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import com.doconnect.service.interfaces.ChatService;

import com.doconnect.repository.ChatMessageRepository;

import com.doconnect.entity.ChatMessage;

import com.doconnect.dto.chat.ChatMessageDTO;
@Service 

@RequiredArgsConstructor 

@Slf4j 

public class ChatServiceImpl 

                implements ChatService { 

 

    private final ChatMessageRepository 

                        chatMessageRepository; 

 

    @Override 

    public ChatMessageDTO saveMessage( 

                    ChatMessageDTO dto) { 

 

        log.info( 

            "Saving message from: {} in room: {}", 

            dto.getSender(), dto.getRoomId()); 

 

        ChatMessage message = ChatMessage 

                .builder() 

                .sender(dto.getSender()) 

                .content(dto.getContent()) 

                .roomId(dto.getRoomId()) 

                .type(dto.getType()) 

                .build(); 

 

        ChatMessage saved = 

            chatMessageRepository.save(message); 

 

        dto.setTimestamp(saved.getTimestamp()); 

        return dto; 

    } 

 

    @Override 

    public List<ChatMessageDTO> getRoomHistory( 

                                String roomId) { 

        return chatMessageRepository 

            .findByRoomIdOrderByTimestampAsc( 

                                        roomId) 

            .stream() 

            .map(this::mapToDTO) 

            .collect(Collectors.toList()); 

    } 

 

    private ChatMessageDTO mapToDTO( 

                            ChatMessage msg) { 

        return ChatMessageDTO.builder() 

                .sender(msg.getSender()) 

                .content(msg.getContent()) 

                .roomId(msg.getRoomId()) 

                .type(msg.getType()) 

                .timestamp(msg.getTimestamp()) 

                .build(); 

    } 

} 