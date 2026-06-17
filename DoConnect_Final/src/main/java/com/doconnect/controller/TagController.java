/** 

 * TagController 

 * Handles tag endpoints 

 */ 

package com.doconnect.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import com.doconnect.service.interfaces.TagService;

import com.doconnect.design_patterns.factory.ResponseFactory;
@RestController 

@RequestMapping("/api/tags") 

@RequiredArgsConstructor 

@Tag(name = "Tags", 

     description = "Tag APIs") 

public class TagController { 

 

    private final TagService tagService; 

 

    @GetMapping 

    @Operation(summary = "Get all tags") 

    public ResponseEntity<Map<String, Object>> 

                getAllTags() { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Tags fetched", 

                    tagService.getAllTags())); 

    } 

 

    @GetMapping("/{name}") 

    @Operation( 

        summary = "Get questions by tag") 

    public ResponseEntity<Map<String, Object>> 

                getByTag( 

                @PathVariable String name) { 

 

        return ResponseEntity.ok( 

                ResponseFactory.success( 

                    "Questions by tag", 

                    tagService 

                        .getQuestionsByTag(name))); 

    } 

} 