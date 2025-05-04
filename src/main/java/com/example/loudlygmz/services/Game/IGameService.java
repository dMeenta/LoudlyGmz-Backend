package com.example.loudlygmz.services.Game;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface IGameService {
    ResponseEntity<?> getGames();
    ResponseEntity<?> getGamesByCategory(int id);
    ResponseEntity<?> getGameById(int id);
    ResponseEntity<?> getUserGames(List<Integer> idList);
}
