package com.example.Recipes.catalog.controllers;

import com.example.Recipes.catalog.models.Recipe;
import com.example.Recipes.catalog.services.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{recipeId}")
    public ResponseEntity<String> addToFavorites(@PathVariable Long recipeId) {
        favoriteService.addFavorite(recipeId);
        return ResponseEntity.ok("Рецепт добавлен в избранное");
    }

    @GetMapping
    public List<Recipe> getAllFavorites() {
        return favoriteService.getAllFavorites();
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<String> deleteFavorites(@PathVariable Long recipeId) {
        favoriteService.deleteFavorite(recipeId);
        return ResponseEntity.ok("Рецепт удалён из избранного");
    }
}
