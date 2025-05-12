package com.example.loudlygmz.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.domain.service.IUserService;
import com.example.loudlygmz.infrastructure.common.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final IUserService userService;

    @GetMapping("/{uid}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUid(@PathVariable String uid) {
        UserResponse user = userService.getUserByUid(uid);
        return ResponseEntity.ok(
            ApiResponse.success(HttpStatus.OK.value(), "Usuario encontrado", user)
        );
    }
}
