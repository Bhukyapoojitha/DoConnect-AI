package com.doconnect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;

import com.doconnect.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/** 

 * User Entity 

 * Maps to 'users' table in database 

 * Represents registered users 

 * 

 * MAPPINGS: 

 * - OneToMany with Question (user posts many questions) 

 * - OneToMany with Answer (user posts many answers) 

 * 

 * VALIDATIONS: 

 * - Username: required, 3-20 chars, alphanumeric 

 * - Email: required, valid format, unique 

 * - Password: required, min 6 chars 

 */ 

@Entity 

@Table(name = "users", 

    uniqueConstraints = { 

        @UniqueConstraint( 

            columnNames = "email"), 

        @UniqueConstraint( 

            columnNames = "username") 

    }) 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

@Builder 

public class User { 

 

    @Id 

    @GeneratedValue( 

        strategy = GenerationType.IDENTITY) 

    private Long id; 

 

    // Username - unique, alphanumeric 

    @NotBlank(message = "Username is required") 

    @Size(min = 3, max = 20, 

        message = "Username must be 3-20 chars") 

    @Pattern(regexp = "^[a-zA-Z0-9_]*$", 

        message = "Only letters, numbers, underscore") 

    @Column(nullable = false, 

            unique = true, 

            length = 20) 

    private String username; 

 

    // Email - unique, valid format 

    @NotBlank(message = "Email is required") 

    @Email(message = "Enter a valid email") 

    @Column(nullable = false, unique = true) 

    private String email; 

 

    // Password - stored as bcrypt hash 

    @NotBlank(message = "Password is required") 

    @Column(nullable = false) 

    private String password; 

 

    // Role - default USER 

    @Enumerated(EnumType.STRING) 

    @Column(nullable = false) 

    @Builder.Default 

    private Role role = Role.USER; 

 

    // Profile fields 

    @Size(max = 200, 

        message = "Bio max 200 characters") 

    @Column(length = 200) 

    private String bio; 

 

    @Column(name = "profile_picture") 

    private String profilePicture; 

 

    @Column(name = "created_at", 

            updatable = false) 

    private LocalDateTime createdAt; 

 

    @Column(name = "updated_at") 

    private LocalDateTime updatedAt; 

 

    // One user has many questions 

    // LAZY - don't load unless needed 

    @OneToMany( 

        mappedBy = "user", 

        cascade = CascadeType.ALL, 

        fetch = FetchType.LAZY, 

        orphanRemoval = true) 

    @JsonIgnore 

    @Builder.Default 

    private List<Question> questions = 

                            new ArrayList<>(); 

 

    // One user has many answers 

    @OneToMany( 

        mappedBy = "user", 

        cascade = CascadeType.ALL, 

        fetch = FetchType.LAZY, 

        orphanRemoval = true) 

    @JsonIgnore 

    @Builder.Default 

    private List<Answer> answers = 

                            new ArrayList<>(); 

 

    // Auto set timestamps 

    @PrePersist 

    public void prePersist() { 

        this.createdAt = LocalDateTime.now(); 

        this.updatedAt = LocalDateTime.now(); 

        if (this.role == null) { 

            this.role = Role.USER; 

        } 

    } 

 

    @PreUpdate 

    public void preUpdate() { 

        this.updatedAt = LocalDateTime.now(); 

    } 

} 