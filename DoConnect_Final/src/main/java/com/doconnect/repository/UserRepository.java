package com.doconnect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doconnect.entity.User;
import com.doconnect.enums.Role;

/** 

* UserRepository 

* Database operations for User entity 

* Extends JpaRepository for CRUD operations 

* 

* DESIGN PATTERN: Singleton 

* Spring manages single instance 

*/ 

@Repository 

public interface UserRepository 

       extends JpaRepository<User, Long> { 



   // Find user by email for login 

   Optional<User> findByEmail(String email); 



   // Find user by username 

   Optional<User> findByUsername( 

                           String username); 



   // Check if email exists for registration 

   Boolean existsByEmail(String email); 



   // Check if username exists 

   Boolean existsByUsername(String username); 



   // Find all users by role 

   List<User> findByRole(Role role); 



   // Search users by username 

   List<User> findByUsernameContaining( 

                           String keyword); 

} 
