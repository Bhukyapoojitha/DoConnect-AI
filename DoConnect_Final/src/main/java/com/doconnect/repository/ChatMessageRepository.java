package com.doconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doconnect.entity.ChatMessage;

/**
 * ChatMessageRepository
 * Database operations for ChatMessage entity
 */
@Repository
public interface ChatMessageRepository 
        extends JpaRepository<ChatMessage, Long> {

    // Get chat history for a room (oldest first)
    List<ChatMessage> findByRoomIdOrderByTimestampAsc(String roomId);

    // Count messages in a room
    long countByRoomId(String roomId);

    // Get latest 50 messages (newest first)
    List<ChatMessage> findTop50ByRoomIdOrderByTimestampDesc(String roomId);
}