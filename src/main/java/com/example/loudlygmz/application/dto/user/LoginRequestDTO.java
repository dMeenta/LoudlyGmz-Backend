package com.example.loudlygmz.application.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Schema to hold email and password to log in LoudlyGmz")
public class LoginRequestDTO {
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email field is required")
    private String email;

    @NotBlank(message = "Password field is required")
    private String password;
}
