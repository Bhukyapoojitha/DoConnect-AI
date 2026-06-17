package com.doconnect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

 

/** 

 * SwaggerConfig 

 * Configures Swagger/OpenAPI documentation 

 * Access at /swagger-ui.html 

 */ 

@Configuration 

public class SwaggerConfig { 

 

    @Bean 

    public OpenAPI doConnectOpenAPI() { 

        return new OpenAPI() 

            .info(new Info() 

                .title("DoConnect AI API") 

                .description( 

                    "Intelligent Discussion & " + 

                    "Knowledge Collaboration Platform") 

                .version("1.0.0")) 

            .addSecurityItem( 

                new SecurityRequirement() 

                    .addList("Bearer Auth")) 

            .components(new Components() 

                .addSecuritySchemes( 

                    "Bearer Auth", 

                    new SecurityScheme() 

                        .type(SecurityScheme.Type.HTTP) 

                        .scheme("bearer") 

                        .bearerFormat("JWT"))); 

    } 

} 