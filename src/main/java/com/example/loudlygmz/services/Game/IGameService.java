package com.example.loudlygmz.services.Game;

import org.springframework.http.ResponseEntity;

public interface IGameService {
    ResponseEntity<?> getGames();
    ResponseEntity<?> getGamesByCategory(int id);
    ResponseEntity<?> getGameById(int id);
}
