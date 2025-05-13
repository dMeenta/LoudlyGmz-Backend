package com.example.loudlygmz.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.auth.PasswordResetRequest;
import com.example.loudlygmz.domain.service.IAuthService;
import com.example.loudlygmz.infrastructure.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/password/reset")
    public ResponseEntity<ApiResponse<Object>> sendResetPasswordEmail(@RequestBody @Valid PasswordResetRequest request) {
        authService.sendPasswordResetEmail(request.getEmail());
        return ResponseEntity.ok(ApiResponse.success(
            HttpStatus.OK.value(),
            "Correo de restablecimiento enviado.",
            null
            ));
    }

}
