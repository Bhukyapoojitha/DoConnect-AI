package com.doconnect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.FetchType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.ArrayList;


/** 

 * Tag Entity 

 * Maps to 'tags' table 

 * Used to categorize questions 

 * 

 * MAPPINGS: 

 * - ManyToMany with Question 

 * 

 * VALIDATIONS: 

 * - Name: required, unique, max 30 chars 

 */ 

@Entity 

@Table(name = "tags", 

    uniqueConstraints = @UniqueConstraint( 

                        columnNames = "name")) 

@Data 

@NoArgsConstructor 

@AllArgsConstructor 

@Builder 

public class Tag { 

 

    @Id 

    @GeneratedValue( 

        strategy = GenerationType.IDENTITY) 

    private Long id; 

 

    // Tag name - unique 

    @NotBlank(message = "Tag name is required") 

    @Size(max = 30, 

        message = "Tag name max 30 characters") 

    @Column(nullable = false, 

            unique = true, 

            length = 30) 

    private String name; 

 

    // Many tags belong to many questions 

    @ManyToMany( 

        mappedBy = "tags", 

        fetch = FetchType.LAZY) 

    @JsonIgnore 

    @Builder.Default 

    private List<Question> questions = 

                            new ArrayList<>(); 

} 