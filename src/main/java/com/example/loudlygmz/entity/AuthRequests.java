package com.example.loudlygmz.entity;

import lombok.Data;

public class AuthRequests {

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data
    public static class RegisterRequest {
        private String email;
        private String password;
        private String displayName;
    }

    @Data
    public static class ResetPasswordRequest {
        private String email;
    }
}
