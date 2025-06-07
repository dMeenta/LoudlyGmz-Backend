package com.example.loudlygmz.application.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
    name = "Login Request Form",
    description = "Login schema to hold email and password and authenticate into LoudlyGmz"
)
public class LoginRequestDTO {

    @Schema(
        example = "example@email.com"
    )
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email field is required")
    private String email;

    @Schema(
        description = "Password of the user"
    )
    @Size(min = 6)
    @NotBlank(message = "Password field is required")
    private String password;
}
