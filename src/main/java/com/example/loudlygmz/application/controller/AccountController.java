package com.example.loudlygmz.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.user.UserRegisterRequest;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.infrastructure.common.ApiResponse;
import com.example.loudlygmz.infrastructure.orchestrator.AccountOrchestrator;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    
    @Autowired
    private AccountOrchestrator accountOrchestrator;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse user = accountOrchestrator.registerUser(request);
        return ResponseEntity.ok(ApiResponse.success(
            HttpStatus.CREATED.value(),
            "Usuario registrado correctamente",
            user));
    }
    
}
