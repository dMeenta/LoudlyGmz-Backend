package com.example.loudlygmz.application.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Schema to hold info from the registration form")
public class RegisterRequestDTO {

    @Schema(description = "User email", example = "example@loudly.com")
    @Email(message = "Email should be a valid value")
    @NotBlank(message = "Email field can not be null or empty")
    private String email;

    @Schema(example = "lorDIsa1asBlank0z?")
    @NotBlank(message = "Username field can not be null or empty")
    @Size(min = 5, max = 30, message = "The length of the username should be between 5 and 30")
    @Pattern(regexp = "^[^\\s]+$", message = "Username must not contain spaces")
    private String username;

    @NotBlank(message = "Password field can not be null or empty")
    private String password;

    private String biography;

    @NotBlank(message = "It actually can, but it musn't")
    private String profilePicture;
}
