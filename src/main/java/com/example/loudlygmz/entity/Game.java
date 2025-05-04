package com.example.loudlygmz.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "games")
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name, description, release_date, developer, game_wallpaper, game_minicard;

    public Game(String name, String description, String release_date, String developer) {
        this.name = name;
        this.description = description;
        this.release_date = release_date;
        this.developer = developer;
        this.game_wallpaper = "";
        this.game_minicard = "";
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "games_categories", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonManagedReference
    private List<Category> categories;
}
