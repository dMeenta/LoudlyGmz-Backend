package com.example.loudlygmz.services.Auth;

import org.springframework.http.ResponseEntity;

import com.example.loudlygmz.entity.AuthRequests;

public interface IAuthService {
    ResponseEntity<?> login(AuthRequests.LoginRequest request);
    ResponseEntity<?> register(AuthRequests.RegisterRequest request);
    ResponseEntity<?> resetPassword(AuthRequests.ResetPasswordRequest request);
}
