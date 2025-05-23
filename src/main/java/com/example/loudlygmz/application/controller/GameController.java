package com.example.loudlygmz.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.game.GameDTO;
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
    public ResponseEntity<ApiResponse<List<GameDTO>>> getGames() {
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                "Juegos obtenidos",
                gameService.getGames()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GameDTO>> getGameById(@PathVariable Integer id) {
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                "Juego encontrado",
                gameService.getGameById(id)));
    }            
    
    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse<List<GameDTO>>> getGamesByCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                "Juegos obtenidos por su categoría",
                gameService.getGamesByCategory(id)));
    }

    public ResponseEntity<ApiResponse<GameDTO>> insertGame(GameDTO game){
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                "Juego insertado correctamente.",
                gameService.insertGame(game)));
    }

}
