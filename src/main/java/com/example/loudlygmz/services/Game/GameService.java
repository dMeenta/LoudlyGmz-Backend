package com.example.loudlygmz.services.Game;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.DAO.IGameDAO;
import com.example.loudlygmz.entity.Game;

@Service
public class GameService implements IGameService {
    @Autowired
    private IGameDAO gameDAO;

    @Override
    public List<Game> getGames() {
        return gameDAO.findAll();
    }

    @Override
    public Optional<Game> getGameById(int id) {
        return gameDAO.findById(id);
    }

    @Override
    public List<Game> getGamesByCategory(int id) {
        return gameDAO.findGamesByCategoryId(id);
    }
}
