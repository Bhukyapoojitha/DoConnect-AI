package com.doconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doconnect.entity.FlaggedContent;
import com.doconnect.enums.ModerationStatus;

/** 

* FlaggedContentRepository 

* Database operations for FlaggedContent entity 

*/ 

@Repository 

public interface FlaggedContentRepository 

       extends JpaRepository 

                   <FlaggedContent, Long> { 



   // Get all pending content 

   List<FlaggedContent> findByStatus( 

                   ModerationStatus status); 



   // Count by status 

   long countByStatus( 

                   ModerationStatus status); 



   // Get by content type 

   List<FlaggedContent> findByContentType( 

                   String contentType); 



   // Check if already flagged 

   boolean existsByContentIdAndContentType( 

                   Long contentId, 

                   String contentType);
   
   

}