package com.example.loudlygmz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.services.Game.IGameService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private IGameService gameService;

    @GetMapping()
    public ResponseEntity<?> getGames() {
        return gameService.getGames();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGameById(@PathVariable int id) {
        return gameService.getGameById(id);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getGamesByCategory(@PathVariable int id) {
        return gameService.getGamesByCategory(id);
    }

}
