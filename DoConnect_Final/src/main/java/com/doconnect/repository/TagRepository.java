package com.doconnect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doconnect.entity.Tag;

/** 

* TagRepository 

* Database operations for Tag entity 

*/ 

@Repository 

public interface TagRepository 

       extends JpaRepository<Tag, Long> { 



   // Find tag by name 

   Optional<Tag> findByName(String name); 



   // Check if tag exists 

   Boolean existsByName(String name); 



   // Search tags by name 

   List<Tag> findByNameContaining( 

                           String keyword); 

}