package com.example.loudlygmz.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.loudlygmz.domain.model.Game;

public interface IGameRepository extends JpaRepository<Game, Integer> {
    @Query("SELECT g FROM Game g JOIN g.categories c WHERE c.id = :categoryId")
    List<Game> findGamesByCategoryId(@Param("categoryId") Integer categoryId);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM categories WHERE id = :categoryId)", nativeQuery = true)
    int categoryExistsRaw(@Param("categoryId") Integer categoryId);
}
