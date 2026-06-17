package com.doconnect.service.interfaces;

import java.util.List;

import com.doconnect.dto.chat.ChatMessageDTO;

public interface ChatService { 

    ChatMessageDTO saveMessage( 

            ChatMessageDTO messageDTO); 

    List<ChatMessageDTO> getRoomHistory( 

            String roomId); 

} 
