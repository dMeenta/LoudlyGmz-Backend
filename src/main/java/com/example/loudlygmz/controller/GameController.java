package com.example.loudlygmz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.services.Game.IGameService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/user")
    public ResponseEntity<?> getUserGames(@RequestBody List<Integer> idList ) {
        return gameService.getUserGames(idList);
    }

}
