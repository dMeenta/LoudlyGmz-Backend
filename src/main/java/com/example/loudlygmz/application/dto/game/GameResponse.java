package com.example.loudlygmz.application.dto.game;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameResponse {
    private int id;
    private String name;
    private String description;
    private LocalDate release_date;
    private String developer;

    private String wallpaper;
    private String card;
    private String banner;
    private String logo;

    private List<String> categories;
}
