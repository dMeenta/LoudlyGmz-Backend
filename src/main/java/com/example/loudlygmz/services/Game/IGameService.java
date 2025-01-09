package com.example.loudlygmz.services.Game;

import java.util.List;
import java.util.Optional;

import com.example.loudlygmz.entity.Game;

public interface IGameService {
    public List<Game> getGames();

    public List<Game> getGamesByCategory(int id);

    public Optional<Game> getGameById(int id);
}
