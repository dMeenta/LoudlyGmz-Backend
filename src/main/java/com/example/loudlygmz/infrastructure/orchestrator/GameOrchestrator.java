package com.example.loudlygmz.infrastructure.orchestrator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.loudlygmz.application.dto.game.GameDTO;
import com.example.loudlygmz.domain.model.Category;
import com.example.loudlygmz.domain.model.Game;
import com.example.loudlygmz.domain.model.GameAssets;
import com.example.loudlygmz.infrastructure.service.impl.CategoryService;
import com.example.loudlygmz.infrastructure.service.impl.GameService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class GameOrchestrator {
  private final GameService gameService;
  private final CategoryService categoryService;

  public GameDTO insertGame(GameDTO gameDTO){
    List<Category> categories = categoryService.getListOfCategoriesByName(gameDTO.getCategories());
    Game game = Game.builder()
    .name(gameDTO.getName())
    .description(gameDTO.getDescription())
    .release_date(gameDTO.getRelease_date())
    .developer(gameDTO.getDeveloper())
    .assets(new GameAssets(gameDTO.getWallpaper(), gameDTO.getCard(), gameDTO.getBanner(), gameDTO.getLogo()))
    .categories(categories)
    .build();

    return gameService.insertGame(game);
  }
}
