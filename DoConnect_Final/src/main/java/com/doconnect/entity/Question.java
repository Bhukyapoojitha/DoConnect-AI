package com.doconnect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/** 

 * Question Entity 

 * Maps to 'questions' table 

 * Core entity of the platform 

 * 

 * MAPPINGS: 

 * - ManyToOne with User (question belongs to user) 

 * - OneToMany with Answer (question has many answers) 

 * - ManyToMany with Tag (question has many tags) 

 * 

 * VALIDATIONS: 

 * - Title: required, 10-150 chars 

 * - Content: required, min 20 chars 

 */ 

@Entity 

@Table(name = "questions") 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

@Builder 

public class Question { 

 

    @Id 

    @GeneratedValue( 

        strategy = GenerationType.IDENTITY) 

    private Long id; 

 

    // Question title 

    @NotBlank(message = "Title is required") 

    @Size(min = 10, max = 150, 

        message = "Title must be 10-150 chars") 

    @Column(nullable = false, length = 150) 

    private String title; 

 

    // Detailed question content 

    @NotBlank(message = "Content is required") 

    @Size(min = 20, 

        message = "Content min 20 characters") 

    @Column(nullable = false, 

            columnDefinition = "TEXT") 

    private String content; 

 

    // Vote count - cannot be negative 

    @Min(value = 0, 

        message = "Votes cannot be negative") 

    @Column(nullable = false) 

    @Builder.Default 

    private int votes = 0; 

 

    // View count 

    @Min(value = 0, 

        message = "Views cannot be negative") 

    @Column(nullable = false) 

    @Builder.Default 

    private int views = 0; 

 

    @Column(name = "created_at", 

            updatable = false) 

    private LocalDateTime createdAt; 

 

    @Column(name = "updated_at") 

    private LocalDateTime updatedAt; 

 

    // Many questions belong to one user 

    // LAZY - load user only when needed 

    @ManyToOne(fetch = FetchType.LAZY) 

    @JoinColumn(name = "user_id", 

                nullable = false) 

    @JsonIgnore 

    private User user; 

 

    // One question has many answers 

    // Delete answers when question deleted 

    @OneToMany( 

        mappedBy = "question", 

        cascade = CascadeType.ALL, 

        fetch = FetchType.LAZY, 

        orphanRemoval = true) 

    @JsonIgnore 

    @Builder.Default 

    private List<Answer> answers = 

                            new ArrayList<>(); 

 

    // Many questions have many tags 

    // Only PERSIST and MERGE - don't delete tags 

    @ManyToMany( 

        fetch = FetchType.LAZY, 

        cascade = { 

            CascadeType.PERSIST, 

            CascadeType.MERGE 

        }) 

    @JoinTable( 

        name = "question_tags", 

        joinColumns = @JoinColumn( 

                        name = "question_id"), 

        inverseJoinColumns = @JoinColumn( 

                        name = "tag_id")) 

    @Builder.Default 

    private List<Tag> tags = new ArrayList<>(); 

 

    @PrePersist 

    public void prePersist() { 

        this.createdAt = LocalDateTime.now(); 

        this.updatedAt = LocalDateTime.now(); 

    } 

 

    @PreUpdate 

    public void preUpdate() { 

        this.updatedAt = LocalDateTime.now(); 

    } 

} 
