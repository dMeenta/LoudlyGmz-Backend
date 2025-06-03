package com.example.loudlygmz.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.infrastructure.common.ApiResponse;
import com.example.loudlygmz.infrastructure.orchestrator.UserOrchestrator;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    
    private final UserOrchestrator userOrchestrator;

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(@PathVariable @Valid
    @Size(min = 5, max = 30, message = "The length of the username should be between 5 and 30")
    @Pattern(regexp = "^[^\\s]+$", message = "Username must not contain spaces")
    @NotBlank(message = "A username is needed to search into LoudlyGmz")
    String username) {
        UserResponse user = userOrchestrator.getUserByUsername(username);
        return ResponseEntity.ok(
            ApiResponse.success(HttpStatus.OK.value(), "Usuario encontrado", user)
        );
    }  
}
