package com.doconnect.security;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.doconnect.entity.User;
import com.doconnect.repository.UserRepository;


 

/** 

 * CustomUserDetailsService 

 * Loads user details for Spring Security 

 * Used during JWT authentication 

 */ 

@Service 

@RequiredArgsConstructor 

@Slf4j 

public class CustomUserDetailsService 

            implements UserDetailsService { 

 

    private final UserRepository userRepository; 

 

    @Override 

    public UserDetails loadUserByUsername( 

                            String email) 

            throws UsernameNotFoundException { 

 

        log.debug( 

            "Loading user by email: {}", email); 

 

        User user = userRepository 

                .findByEmail(email) 

                .orElseThrow(() -> 

                    new UsernameNotFoundException( 

                        "User not found: " + email)); 

 

        return org.springframework.security 

                .core.userdetails.User 

                .builder() 

                .username(user.getEmail()) 

                .password(user.getPassword()) 

                .roles(user.getRole().name()) 

                .build(); 

    } 

}