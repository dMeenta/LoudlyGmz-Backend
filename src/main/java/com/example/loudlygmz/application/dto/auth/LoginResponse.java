package com.example.loudlygmz.application.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String uid;
    private String idToken;
    private String refreshToken;
    private long expiresIn;
}
