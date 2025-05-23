package com.example.loudlygmz.domain.service;

import java.util.List;

import com.example.loudlygmz.application.dto.game.GameDTO;
import com.example.loudlygmz.domain.model.Game;

public interface IGameService {
    List<GameDTO> getGames();
    GameDTO getGameById(Integer id);
    GameDTO insertGame(Game game);
    List<GameDTO> getGamesByCategory(Integer id);
    List<GameDTO> getListOfGamesById(List<Integer> idList);
}
