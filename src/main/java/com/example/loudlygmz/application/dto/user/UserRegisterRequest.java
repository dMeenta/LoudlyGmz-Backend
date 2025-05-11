package com.example.loudlygmz.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterRequest {
    @Email @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String biography;

    @NotBlank
    private String profilePicture;
}
