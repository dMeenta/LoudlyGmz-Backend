package com.example.loudlygmz.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.user.RegisterRequestDTO;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.infrastructure.common.ApiResponse;
import com.example.loudlygmz.infrastructure.orchestrator.AccountOrchestrator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountOrchestrator accountOrchestrator;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequestDTO request) {
        UserResponse response = accountOrchestrator.registerUser(request);
        return ResponseEntity.ok(ApiResponse.success(
            HttpStatus.CREATED.value(),
            "Usuario registrado correctamente",
            response));
    }
}
