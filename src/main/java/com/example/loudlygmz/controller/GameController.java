package com.example.loudlygmz.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.entity.Game;
import com.example.loudlygmz.services.Game.IGameService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private IGameService gameService;

    @GetMapping()
    public List<Game> getGames() {
        return gameService.getGames();
    }

    @GetMapping("/{id}")
    public Optional<Game> getGameById(@PathVariable int id) {
        return gameService.getGameById(id);
    }

    @GetMapping("/category/{id}")
    public List<Game> getGamesByCategory(@PathVariable int id) {
        return gameService.getGamesByCategory(id);
    }

}
