package com.example.loudlygmz.domain.service;

import java.util.List;

import com.example.loudlygmz.application.dto.GameResponse;

public interface IGameService {
    List<GameResponse> getGames();
    GameResponse getGameById(int id);
    List<GameResponse> getGamesByCategory(int id);
    List<GameResponse> getListOfGamesById(List<Integer> idList);
}
