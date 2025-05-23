package com.example.loudlygmz.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Builder
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class GameAssets {
    private String wallpaper;
    private String card;
    private String banner;
    private String logo;
}