package com.example.loudlygmz.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.LoginResponse;
import com.example.loudlygmz.application.dto.user.UserLoginRequest;
import com.example.loudlygmz.application.dto.user.UserRegisterRequest;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.infrastructure.common.ApiResponse;
import com.example.loudlygmz.infrastructure.orchestrator.AccountOrchestrator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountOrchestrator accountOrchestrator;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse response = accountOrchestrator.registerUser(request);
        return ResponseEntity.ok(ApiResponse.success(
            HttpStatus.CREATED.value(),
            "Usuario registrado correctamente",
            response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody UserLoginRequest request) {
        LoginResponse response = accountOrchestrator.login(request);
        return ResponseEntity.ok(ApiResponse.success(
            HttpStatus.OK.value(),
            "Inicio de sesión exitoso",
            response));
    }
}
