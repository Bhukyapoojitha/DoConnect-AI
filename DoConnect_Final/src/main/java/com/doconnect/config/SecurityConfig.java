package com.doconnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

import java.util.List;

import com.doconnect.security.JwtAuthFilter;


 

/** 

 * SecurityConfig 

 * Configures Spring Security 

 * Sets up JWT filter, CORS, public endpoints 

 * 

 * DESIGN PATTERN: Singleton 

 * Single security configuration 

 */ 

@Configuration 

@EnableWebSecurity 

@EnableMethodSecurity 

@RequiredArgsConstructor 

public class SecurityConfig { 

 

    private final JwtAuthFilter jwtAuthFilter; 

    private final UserDetailsService 

                        userDetailsService; 

 

    @Bean 

    public SecurityFilterChain filterChain( 

                HttpSecurity http) 

                throws Exception { 

        http 

            // Disable CSRF for REST APIs 

            .csrf(csrf -> csrf.disable()) 

 

            // Configure CORS 

            .cors(cors -> cors 

                .configurationSource( 

                    corsConfigurationSource())) 

 

            // Configure endpoint access 

            .authorizeHttpRequests(auth -> auth 

                // Public endpoints 

                .requestMatchers( 

                    "/api/auth/**", 

                    "/swagger-ui/**", 

                    "/swagger-ui.html", 

                    "/v3/api-docs/**", 

                    "/ws/**",           // ← ADD THIS 

                    "/ws",              // ← ADD THIS 

                    "/topic/**",        // ← ADD THIS 

                    "/app/**") 

                .permitAll() 

                // Admin only endpoints 

                .requestMatchers( 

                    "/api/admin/**", 

                    "/api/analytics/**") 

                .hasRole("ADMIN") 

                // Moderator endpoints 

                .requestMatchers( 

                    "/api/moderation/**") 

                .hasAnyRole("ADMIN","MODERATOR") 

                // All other endpoints need auth 

                .anyRequest().authenticated() 

            ) 

 

            // Stateless session 

            .sessionManagement(session -> 

                session.sessionCreationPolicy( 

                    SessionCreationPolicy.STATELESS)) 

 

            // Add JWT filter 

            .authenticationProvider( 

                authenticationProvider()) 

            .addFilterBefore( 

                jwtAuthFilter, 

                UsernamePasswordAuthenticationFilter 

                    .class); 

 

        return http.build(); 

    } 

 

    // CORS configuration 

    @Bean 

    public CorsConfigurationSource 

                corsConfigurationSource() { 

        CorsConfiguration config = 

                        new CorsConfiguration(); 

        config.setAllowedOrigins( 

            List.of("http://localhost:3000")); 

        config.setAllowedMethods( 

            List.of("GET","POST", 

                    "PUT","DELETE","OPTIONS")); 

        config.setAllowedHeaders(List.of("*")); 

        config.setAllowCredentials(true); 

 

        UrlBasedCorsConfigurationSource source = 

            new UrlBasedCorsConfigurationSource(); 

        source.registerCorsConfiguration( 

                                "/**", config); 

        return source; 

    } 
    
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

 

    @Bean 

    public PasswordEncoder passwordEncoder() { 

        return new BCryptPasswordEncoder(); 

    } 

 

    @Bean 

    public AuthenticationManager 

                authenticationManager( 

                AuthenticationConfiguration config) 

                throws Exception { 

        return config.getAuthenticationManager(); 

    } 

} 

 