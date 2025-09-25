package com.example.Recipes.catalog.controllers;

import com.example.Recipes.catalog.models.Recipe;
import com.example.Recipes.catalog.services.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/recipes/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService service;

    @PostMapping("/{recipeId}")
    public ResponseEntity<String> addToFavorites(@PathVariable Long recipeId) {
        service.addFavorite(recipeId);
        return ResponseEntity.ok("Рецепт добавлен в избранное");
    }

    @GetMapping
    public List<Recipe> getAllFavorites() {
        return service.getAllFavorites();
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<String> deleteFavorites(@PathVariable Long recipeId) {
        service.deleteFavorite(recipeId);
        return ResponseEntity.ok("Рецепт удалён из избранного");
    }

    @GetMapping("/sortedNameAsc")
    public ResponseEntity<List<Recipe>> getFindAllSortedNameAsc() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllSortedNameAsc());
    }

    @GetMapping("/sortedNameDesc")
    public ResponseEntity<List<Recipe>> getFindAllSortedNameDesc() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllSortedNameDesc());
    }

    @GetMapping("/sortedCookingTimeAsc")
    public ResponseEntity<List<Recipe>> getFindAllSortedCookingTimeAsc() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllSortedCookingTimeAsc());
    }

    @GetMapping("/sortedCookingTimeDesc")
    public ResponseEntity<List<Recipe>> getFindAllSortedCookingTimeDesc() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllSortedCookingTimeDesc());
    }
}
