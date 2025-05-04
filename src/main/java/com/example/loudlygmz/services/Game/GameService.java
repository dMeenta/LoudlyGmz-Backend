package com.example.loudlygmz.services.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.DAO.IGameDAO;
import com.example.loudlygmz.entity.Game;
import com.example.loudlygmz.utils.ApiResponse;

@Service
public class GameService implements IGameService {
    
    @Autowired
    private IGameDAO gameDAO;

    @Override
    public ResponseEntity<?> getGames() {
        try {
            List<Game> response = gameDAO.findAll();
            return ResponseEntity.ok(ApiResponse.success(
                HttpStatus.OK.value(),
                "Juegos obtenidos exitosamente",
                response
                ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error de Servidor",
                    e.getMessage())
            );
        }
    }

    @Override
    public ResponseEntity<?> getGameById(int id) {
        try {
            Optional<Game> response = gameDAO.findById(id);
            if(!response.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(),
                        "Juego no encontrado",
                        null)
                );
            }
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Juego Encontrado Exitosamente", response));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error de servidor",
                    e.getMessage())
            );
        }
    }

    @Override
    public ResponseEntity<?> getGamesByCategory(int id) {
        try {
            List<Game> response = gameDAO.findGamesByCategoryId(id);
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Juegos obtenidos por categoría", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error de servidor",
                e.getMessage())
            );
        }
    }

    @Override
    public ResponseEntity<?> getUserGames(List<Integer> idList) {
        try {
            List<Game> response = new ArrayList<Game>();
            for (Integer id : idList) {
                Optional<Game> game = gameDAO.findById(id);
                game.ifPresent(response::add);
            }
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Juegos del usuario obtenidos exitosamente", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener los juegos del usuario", null));
        }
    }
}
