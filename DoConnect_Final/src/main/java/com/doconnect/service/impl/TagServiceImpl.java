/** 

 * TagServiceImpl 

 * Handles tag operations 

 */ 
package com.doconnect.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import com.doconnect.service.interfaces.TagService;

import com.doconnect.repository.TagRepository;
import com.doconnect.repository.QuestionRepository;
import com.doconnect.dto.question.QuestionResponseDTO;
import com.doconnect.entity.Tag;


@Service 

@RequiredArgsConstructor 

@Slf4j 

public class TagServiceImpl 

                implements TagService { 

 

    private final TagRepository tagRepository; 

    private final QuestionRepository 

                        questionRepository; 

 

    @Override 

    public List<Tag> getAllTags() { 

        return tagRepository.findAll(); 

    } 

 

    @Override 

    public List<QuestionResponseDTO> 

                    getQuestionsByTag( 

                    String tagName) { 

        return questionRepository 

                .findByTagName(tagName) 

                .stream() 

                .map(q -> QuestionResponseDTO 

                    .builder() 

                    .id(q.getId()) 

                    .title(q.getTitle()) 

                    .content(q.getContent()) 

                    .username( 

                        q.getUser().getUsername()) 

                    .votes(q.getVotes()) 

                    .views(q.getViews()) 

                    .tags(q.getTags().stream() 

                        .map(Tag::getName) 

                        .collect( 

                          Collectors.toList())) 

                    .createdAt(q.getCreatedAt()) 

                    .build()) 

                .collect(Collectors.toList()); 

    } 

}