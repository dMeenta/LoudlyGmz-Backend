package com.example.loudlygmz.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.game.GameDTO;
import com.example.loudlygmz.domain.service.IGameService;
import com.example.loudlygmz.infrastructure.common.ResponseDTO;
import com.example.loudlygmz.infrastructure.orchestrator.GameOrchestrator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(
    name = "Games Controller",
    description = "Controller for the management of games on LoudlyGmz MySQL database"
)
@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
@Validated
public class GameController {

    private final GameOrchestrator gameOrchestrator;
    private final IGameService gameService;

    @Operation(
        summary = "List all Games endpoint",
        description = "Endpoint to List All Games registered on msql database"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Http Status OK"
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<List<GameDTO>>> getGames() {
        return ResponseEntity.ok(
            ResponseDTO.success(
                HttpStatus.OK.value(),
                "Juegos obtenidos",
                gameService.getGames()));
    }

    @Operation(
        summary = "Get a Game By ID endpoint",
        description = "Get a game data by its ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Http Status OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<GameDTO>> getGameById(@Schema(example = "1") @PathVariable Integer id) {
        return ResponseEntity.ok(
            ResponseDTO.success(
                HttpStatus.OK.value(),
                "Juego encontrado",
                gameService.getGameById(id)));
    }
    
    @Operation(
        summary = "List All Games By Their Category endpoint",
        description = "Endpoint to List All Games that match with the category name received"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Http Status OK"
    )
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<ResponseDTO<List<GameDTO>>> getGamesByCategory(@Schema(example = "metroidvania")
        @PathVariable String categoryName) {
        return ResponseEntity.ok(
            ResponseDTO.success(
                HttpStatus.OK.value(),
                "Juegos obtenidos por su categoría",
                gameService.getGamesByCategory(categoryName)));
    }

    @Operation(
        summary = "Insert a new a Game",
        description = "System verified if you are admin o not, Only ADMINS can register a new game"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "201",
        description = "Http Status CREATED"
    )
    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO<GameDTO>> insertGame(@Valid @RequestBody GameDTO game){
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseDTO.success(
                HttpStatus.CREATED.value(),
                "Juego registrado correctamente.",
                gameOrchestrator.insertGame(game)));
    }

}
