package com.example.loudlygmz.infrastructure.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.GameResponse;
import com.example.loudlygmz.domain.model.Category;
import com.example.loudlygmz.domain.model.Game;
import com.example.loudlygmz.domain.repository.IGameRepository;
import com.example.loudlygmz.domain.service.IGameService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {
    
    private final IGameRepository gameRepository;

    @Override
    public List<GameResponse> getGames() {
        return gameRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public GameResponse getGameById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del juego debe ser mayor que cero.");
        }

        return gameRepository.findById(id)
            .map(this::toResponse)
            .orElseThrow(() -> new EntityNotFoundException("Juego no encontrado"));
    }

    @Override
    public List<GameResponse> getGamesByCategory(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la categoría debe ser mayor que cero.");
        }

        List<GameResponse> games = gameRepository.findGamesByCategoryId(id).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
        
        if (games.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron juegos en la categoría.");
        }
        return games;
    }

    @Override
    public List<GameResponse> getListOfGamesById(List<Integer> idList) {
        for (Integer id : idList) {
            if(id<=0){
                throw new IllegalArgumentException("El ID:"+ id +" debe ser mayor que cero.");
            }    
        }
        return gameRepository.findAllById(idList).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
    }

    private GameResponse toResponse(Game game) {
        return GameResponse.builder()
        .id(game.getId())
        .name(game.getName())
        .description(game.getDescription())
        .release_date(game.getRelease_date())
        .developer(game.getDeveloper())
        .wallpaper(game.getAssets().getWallpaper())
        .card(game.getAssets().getCard())
        .banner(game.getAssets().getBanner())
        .logo(game.getAssets().getLogo())
        .categories(game.getCategories().stream()
            .map(Category::getName)
            .toList())
        .build();
    }
}
