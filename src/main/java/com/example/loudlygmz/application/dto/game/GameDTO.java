package com.example.loudlygmz.application.dto.game;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameDTO {
    @Schema(
        example="Roblox"
    )
    @NotBlank(message = "Game's name cannot be empty or null")
    private String name;

    @Schema(
        example="This is a description of the game you try to insert into our system"
    )
    @NotBlank(message = "Game's desc cannot be empty or null")
    private String description;

    @Schema(
        example="2009-10-27"
    )
    @NotNull(message = "Game's release date cannot be null")
    private LocalDate release_date;

    @Schema(
        example="Indie Dev"
    )
    @NotBlank(message = "Game's developers cannot be empty or null")
    private String developer;

    private String wallpaper;
    private String card;
    private String banner;
    private String logo;
    @NotEmpty(message = "At least one category is required")
    private List<@NotBlank(message = "Category name cannot be blank") String> categories;
}
