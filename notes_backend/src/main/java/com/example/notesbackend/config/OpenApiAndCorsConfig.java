package com.example.notesbackend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * OpenAPI metadata and global CORS configuration.
 */
@Configuration
public class OpenApiAndCorsConfig {

    @Bean
    public OpenAPI notesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Notes API")
                        .version("0.1.0")
                        .description("REST API for managing notes (create, read, update, delete).")
                        .contact(new Contact().name("Notes Service").email("noreply@example.com")))
                .externalDocs(new ExternalDocumentation().description("Swagger UI").url("/swagger-ui.html"));
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        // For demo purposes, allow all origins/methods. Adjust for production as needed.
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedOrigins("*")
                        .allowedHeaders("*");
            }
        };
    }
}
