package com.example.loudlygmz.application.dto.game;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameDTO {

    @NotBlank(message = "Game's name cannot be empty or null")
    private String name;
    @NotBlank(message = "Game's desc cannot be empty or null")
    private String description;

    @NotNull(message = "Game's release date cannot be null")
    private LocalDate release_date;

    @NotBlank(message = "Game's developers cannot be empty or null")
    private String developer;

    private String wallpaper;
    private String card;
    private String banner;
    private String logo;

    @NotEmpty(message = "At least one category is required")
    private List<@NotBlank(message = "Category name cannot be blank") String> categories;
}
