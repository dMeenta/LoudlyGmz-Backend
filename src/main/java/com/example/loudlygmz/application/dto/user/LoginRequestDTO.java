package com.example.loudlygmz.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email field is required")
    private String email;

    @NotBlank(message = "Password field is required")
    private String password;
}
