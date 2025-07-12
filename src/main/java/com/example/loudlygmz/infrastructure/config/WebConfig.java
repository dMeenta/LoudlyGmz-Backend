package com.example.loudlygmz.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${frontend.url}")
    private String frontendUrls;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = frontendUrls.split(",");
        registry.addMapping("/**")
            .allowedOrigins(origins)
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
    
}
