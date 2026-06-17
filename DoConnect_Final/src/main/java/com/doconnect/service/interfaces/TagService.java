package com.doconnect.service.interfaces;

import java.util.List;

import com.doconnect.dto.question.QuestionResponseDTO;
import com.doconnect.entity.Tag;

public interface TagService { 

    List<Tag> getAllTags(); 

    List<QuestionResponseDTO> getQuestionsByTag( 

            String tagName); 

} 
