package com.example.loudlygmz.infrastructure.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.game.GameDTO;
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
    public List<GameDTO> getGames() {
        return gameRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public GameDTO getGameById(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID del juego debe ser mayor que cero.");
        }
        return gameRepository.findById(id)
            .map(this::toResponse)
            .orElseThrow(() -> new EntityNotFoundException(
                String.format("Juego con ID: %s no encontrado", id)));
    }

    public Game getGameByNameWithId(String gameName) {
        return gameRepository.findByName(gameName).orElseThrow(() -> new EntityNotFoundException(
            String.format("Juego con nombre: %s no encontrado", gameName)));
    }

    public GameDTO getGameByName(String gameName) {
        return gameRepository.findByName(gameName).map(this::toResponse)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("Juego con nombre: %s no encontrado", gameName)));
    }

    @Override
    public List<GameDTO> getGamesByCategory(String categoryName) {
        boolean categoryExists = gameRepository.categoryExistsRaw(categoryName);

        if(!categoryExists){
            throw new EntityNotFoundException(String.format("La categoría con '%s' no existe", categoryName));
        }

        List<GameDTO> games = gameRepository.findGamesByCategoryName(categoryName).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());

        return games;
    }

    @Override
    public List<GameDTO> getListOfGamesById(List<Integer> idList) {
        for (Integer id : idList) {
            if(id<=0){
                throw new IllegalArgumentException("El ID:"+ id +" debe ser mayor que cero.");
            }    
        }
        return gameRepository.findAllById(idList).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
    }

    @Override
    public GameDTO insertGame(Game game) {
        return toResponse(gameRepository.save(game));
    }

    @Override
    public Page<Game> getListOfGamesById(List<Integer> idList, Pageable pageable) {
        return gameRepository.findByIdIn(idList, pageable);
    }

    private GameDTO toResponse(Game game) {
        return GameDTO.builder()
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
