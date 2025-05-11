package com.example.loudlygmz.application.dto.login;

import com.example.loudlygmz.application.dto.user.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String idToken;
    private String refreshToken;
    private long expiresIn;
    private UserResponse user;
}
