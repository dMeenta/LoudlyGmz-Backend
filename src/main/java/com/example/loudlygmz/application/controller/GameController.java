package com.example.loudlygmz.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.game.GameDTO;
import com.example.loudlygmz.domain.service.IGameService;
import com.example.loudlygmz.infrastructure.common.ApiResponse;
import com.example.loudlygmz.infrastructure.orchestrator.GameOrchestrator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
@Validated
public class GameController {

    private final GameOrchestrator gameOrchestrator;
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


    @PostMapping("/insert")
    public ResponseEntity<ApiResponse<GameDTO>> insertGame(@Valid @RequestBody GameDTO game){
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success(
                HttpStatus.CREATED.value(),
                "Juego registrado correctamente.",
                gameOrchestrator.insertGame(game)));
    }

}
