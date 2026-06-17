package com.doconnect.service.interfaces;

import java.util.List;
import java.util.Map;

import com.doconnect.entity.FlaggedContent;
import com.doconnect.enums.ModerationStatus;

public interface ModerationService { 

    FlaggedContent flagContent( 

            String contentType, 

            Long contentId, 

            String content, 

            String reason); 

    List<FlaggedContent> getPendingContent(); 

    FlaggedContent updateStatus( 

            Long id, 

            ModerationStatus status); 
    Map<String, Long> getModerationStats();

} 