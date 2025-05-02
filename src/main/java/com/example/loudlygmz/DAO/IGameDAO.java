package com.example.loudlygmz.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.loudlygmz.entity.Game;

public interface IGameDAO extends JpaRepository<Game, Integer> {
    @Query("SELECT g FROM Game g JOIN g.categories c WHERE c.id = :categoryId")
    List<Game> findGamesByCategoryId(@Param("categoryId") int categoryId);
}
