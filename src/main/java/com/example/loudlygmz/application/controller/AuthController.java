package com.example.loudlygmz.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.auth.LoginResponse;
import com.example.loudlygmz.application.dto.user.LoginRequestDTO;
import com.example.loudlygmz.domain.service.IAuthService;
import com.example.loudlygmz.infrastructure.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final IAuthService authService;

    @Operation(
        summary = "Log-in API",
        description = "Rest API for users to log in LoudlyGmz"
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(
            HttpStatus.OK.value(),
            "Inicio de sesión exitoso",
            response));
    }

}
