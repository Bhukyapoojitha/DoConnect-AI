package com.doconnect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doconnect.entity.Answer;

/** 

 * AnswerRepository 

 * Database operations for Answer entity 

 */ 

@Repository 

public interface AnswerRepository 

        extends JpaRepository<Answer, Long> { 

 

    // Get all answers for a question 

    List<Answer> findByQuestionId( 

                            Long questionId); 

 

    // Get all answers by user 

    List<Answer> findByUserId(Long userId); 

 

    // Get accepted answer for question 

    Optional<Answer> findByQuestionIdAndIsAcceptedTrue( 

                            Long questionId); 

 

    // Count answers by user 

    long countByUserId(Long userId); 

 

    // Count answers for question 

    long countByQuestionId(Long questionId); 

}
