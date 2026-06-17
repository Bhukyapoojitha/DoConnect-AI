package com.doconnect.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import com.doconnect.enums.ModerationStatus;
import lombok.*;
import java.time.LocalDateTime;


/** 

 * FlaggedContent Entity 

 * Maps to 'flagged_content' table 

 * Stores AI-detected or user-reported 

 * inappropriate content for review 

 * 

 * VALIDATIONS: 

 * - ContentType: required 

 * - Content: required 

 * - Reason: required 

 */ 

@Entity 

@Table(name = "flagged_content") 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

@Builder 

public class FlaggedContent { 

 

    @Id 

    @GeneratedValue( 

        strategy = GenerationType.IDENTITY) 

    private Long id; 

 

    // Type: QUESTION or ANSWER 

    @NotBlank( 

        message = "Content type is required") 

    @Column(name = "content_type", 

            nullable = false) 

    private String contentType; 

 

    // ID of flagged question/answer 

    @NotNull(message = "Content ID is required") 

    @Column(name = "content_id", 

            nullable = false) 

    private Long contentId; 

 

    // Actual flagged text 

    @NotBlank(message = "Content is required") 

    @Column(nullable = false, 

            columnDefinition = "TEXT") 

    private String content; 

 

    // Why it was flagged 

    @NotBlank(message = "Reason is required") 

    @Column(nullable = false) 

    private String reason; 

 

    // PENDING / APPROVED / REJECTED 

    @Enumerated(EnumType.STRING) 

    @Column(nullable = false) 

    @Builder.Default 

    private ModerationStatus status = 

                    ModerationStatus.PENDING; 

 

    @Column(name = "flagged_at", 

            updatable = false) 

    private LocalDateTime flaggedAt; 

 

    @PrePersist 

    public void prePersist() { 

        this.flaggedAt = LocalDateTime.now(); 

        if (this.status == null) { 

            this.status = ModerationStatus.PENDING; 

        } 

    } 

} 
