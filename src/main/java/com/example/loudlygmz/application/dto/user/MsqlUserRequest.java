package com.example.loudlygmz.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MsqlUserRequest {
    @NotBlank
    private String uid;

    @Email @NotBlank
    private String email;

    @NotBlank
    private String username;

    private String biography;

    @NotBlank
    private String profilePicture;
}
