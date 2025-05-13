package com.example.loudlygmz.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.game.GameResponse;
import com.example.loudlygmz.domain.service.IGameService;
import com.example.loudlygmz.infrastructure.common.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final IGameService gameService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GameResponse>>> getGames() {
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                "Juegos obtenidos",
                gameService.getGames()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GameResponse>> getGameById(@PathVariable int id) {
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                "Juego encontrado",
                gameService.getGameById(id)));
    }            
    
    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse<List<GameResponse>>> getGamesByCategory(@PathVariable int id) {
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                "Juegos obtenidos por su categoría",
                gameService.getGamesByCategory(id)));
    }

}
