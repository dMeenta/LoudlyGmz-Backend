package com.example.loudlygmz.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.loudlygmz.entity.Game;

public interface IGameDAO extends JpaRepository<Game, Integer> {
    /*
     * @Query(value = "SELECT g.* FROM games g " +
     * "JOIN games_categories gc ON g.id = gc.game_id " +
     * "WHERE gc.category_id = :categoryId", nativeQuery = true)
     * List<Game> findGamesByCategoryId(@Param("categoryId") int categoryId);
     */
    @Query("SELECT g FROM Game g JOIN g.categories c WHERE c.id = :categoryId")
    List<Game> findGamesByCategoryId(@Param("categoryId") int categoryId);
}
