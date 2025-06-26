package com.example.loudlygmz.application.controller;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.auth.LoginResponse;
import com.example.loudlygmz.application.dto.user.LoginRequestDTO;
import com.example.loudlygmz.domain.service.IAuthService;
import com.example.loudlygmz.infrastructure.common.ResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(
    name = "Auth Controller",
    description = "Controller for the management of sessions on LoudlyGmz"
)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final IAuthService authService;

    @Operation(
        summary = "Login endpoint",
        description = "Endpoint to log in LoudlyGmz system\n Now with Cookies for more security!"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Http Status OK"
    )
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<LoginResponse>> login(@Valid @RequestBody LoginRequestDTO request,
    HttpServletResponse servletResponse) {
        LoginResponse response = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("session", response.getIdToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofSeconds(response.getExpiresIn()))
            .sameSite("None")
            .build();

        servletResponse.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(ResponseDTO.success(
            HttpStatus.OK.value(),
            "Inicio de sesión exitoso",
            response));
    }

}
