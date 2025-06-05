package com.example.loudlygmz.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.loudlygmz.infrastructure.security.FirebaseAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final FirebaseAuthFilter firebaseAuthFilter;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    return http.csrf(csrf -> csrf.disable())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// sin sesión por defecto
      .authorizeHttpRequests(auth->{
        auth.requestMatchers("/api/games/insert").hasRole("ADMIN"); // 🔐 Solo ADMIN puede insertar
        auth.anyRequest().permitAll();
      })
      .addFilterBefore(firebaseAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .build();
  }
}
