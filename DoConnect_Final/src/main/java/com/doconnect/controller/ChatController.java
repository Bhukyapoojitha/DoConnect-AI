/**
 * ChatController
 * FIXED VERSION
 * No Principal (no null error)
 * Correct topic paths
 * Matches frontend useWebSocket
 */

package com.doconnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.doconnect.service.interfaces.ChatService;
import com.doconnect.dto.chat.ChatMessageDTO;
import com.doconnect.enums.MessageType;
import com.doconnect.design_patterns.factory.ResponseFactory;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    // MAIN CHAT MESSAGE HANDLER
    @MessageMapping("/chat/{roomId}")
    public void sendMessage(
            @DestinationVariable String roomId,
            @Payload ChatMessageDTO messageDTO) {

        // DO NOT USE PRINCIPAL

        messageDTO.setRoomId(roomId);

        // Use sender coming from frontend
        if (messageDTO.getSender() == null || messageDTO.getSender().isEmpty()) {
            messageDTO.setSender("Anonymous");
        }

        messageDTO.setType(MessageType.CHAT);

        // Save in DB
        ChatMessageDTO saved = chatService.saveMessage(messageDTO);  //  DECLARED HERE

        //  USE IT ONLY AFTER DECLARATION (same block)
        if (saved != null && (saved.getSender() == null || saved.getSender().isEmpty())) {
            saved.setSender(messageDTO.getSender());
        }

        // CORRECT TOPIC (IMPORTANT)
        messagingTemplate.convertAndSend(
                "/topic/room/" + roomId,
                saved
        );

        log.info(" Message sent in room: {}", roomId);
    }



    //  REST: Get history
    @GetMapping("/api/chat/{roomId}/history")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getHistory(
            @PathVariable String roomId) {

        return ResponseEntity.ok(
                ResponseFactory.success(
                        "Chat history fetched",
                        chatService.getRoomHistory(roomId)
                )
        );
    }
}
