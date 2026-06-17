/** 

 * ModerationServiceImpl 

 * Handles content moderation operations 

 * Admin/Moderator use this to review flags 

 */ 

package com.doconnect.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import com.doconnect.service.interfaces.ModerationService;

import com.doconnect.repository.FlaggedContentRepository;
import com.doconnect.repository.QuestionRepository;

import com.doconnect.entity.FlaggedContent;
import com.doconnect.enums.ModerationStatus;

import com.doconnect.exception.ResourceNotFoundException;

@Service 

@RequiredArgsConstructor 

@Slf4j 

public class ModerationServiceImpl 

                implements ModerationService { 

 

    private final FlaggedContentRepository 

                        flaggedContentRepository; 

    private final QuestionRepository 

                        questionRepository; 

 

    @Override 

    public FlaggedContent flagContent( 

                String contentType, 

                Long contentId, 

                String content, 

                String reason) { 

 

        // Don't flag same content twice 

        if (flaggedContentRepository 

                .existsByContentIdAndContentType( 

                    contentId, contentType)) { 

            throw new IllegalArgumentException( 

                "Content already flagged!"); 

        } 

 

        FlaggedContent flagged = 

            FlaggedContent.builder() 

                .contentType(contentType) 

                .contentId(contentId) 

                .content(content) 

                .reason(reason) 

                .build(); 

 

        log.info( 

            "Content flagged: {} id: {}", 

            contentType, contentId); 

        return flaggedContentRepository 

                            .save(flagged); 

    } 

 

    @Override 

    public List<FlaggedContent> 

                        getPendingContent() { 

        return flaggedContentRepository 

                .findByStatus( 

                    ModerationStatus.PENDING); 

    } 

    @Override
    public Map<String, Long> getModerationStats() {

        long pending = flaggedContentRepository
                .countByStatus(ModerationStatus.PENDING);

        long approved = flaggedContentRepository
                .countByStatus(ModerationStatus.APPROVED);

        long rejected = flaggedContentRepository
                .countByStatus(ModerationStatus.REJECTED);

        return Map.of(
                "pending", pending,
                "approved", approved,
                "rejected", rejected
        );
    }

    @Override 

    public FlaggedContent updateStatus( 

                Long id, 

                ModerationStatus status) { 

 

        FlaggedContent flagged = 

            flaggedContentRepository 

                .findById(id) 

                .orElseThrow(() -> 

                    new ResourceNotFoundException( 

                        "Flagged content not found: " 

                        + id)); 

 

        flagged.setStatus(status); 

 

        // Delete rejected questions 

        if (status == ModerationStatus.REJECTED 

            && "QUESTION".equals( 

                flagged.getContentType())) { 

            questionRepository.deleteById( 

                flagged.getContentId()); 

            log.info( 

                "Rejected question deleted: {}", 

                flagged.getContentId()); 

        } 

 

        return flaggedContentRepository 

                            .save(flagged); 

    } 

} 