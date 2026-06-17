package com.doconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doconnect.entity.Question;

/** 

 * QuestionRepository 

 * Database operations for Question entity 

 */ 

@Repository 

public interface QuestionRepository 

        extends JpaRepository<Question, Long> { 

 

    // Get all questions by user 

    List<Question> findByUserId(Long userId); 

 

    // Search by title keyword 

    List<Question> findByTitleContaining( 

                            String keyword); 

 

    // Top 10 trending by views 

    List<Question> findTop10ByOrderByViewsDesc(); 

 

    // Top 10 by votes 

    List<Question> findTop10ByOrderByVotesDesc(); 

 

    // Questions by tag name 

    @Query("SELECT q FROM Question q " + 

           "JOIN q.tags t " + 

           "WHERE t.name = :tagName") 

    List<Question> findByTagName( 

                    @Param("tagName")  

                    String tagName); 

 

    // Count questions by user 

    long countByUserId(Long userId); 

}
