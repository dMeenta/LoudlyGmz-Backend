package com.example.loudlygmz.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.loudlygmz.domain.model.Game;

public interface IGameRepository extends JpaRepository<Game, Integer> {
    @Query("SELECT g FROM Game g JOIN g.categories c WHERE c.name = :categoryName")
    List<Game> findGamesByCategoryName(@Param("categoryName") String categoryName);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM categories WHERE name = :categoryName)", nativeQuery = true)
    int categoryExistsRaw(@Param("categoryName") String categoryName);
}
