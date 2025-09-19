package com.example.Recipes.catalog.controllers;

import com.example.Recipes.catalog.models.Recipe;
import com.example.Recipes.catalog.models.RecipeSearch;
import com.example.Recipes.catalog.services.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/recipes")
public class RecipeController {
    private final RecipeService service;

    @Autowired
    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@RequestBody @Valid Recipe recipe) {
        Recipe saved = service.saveRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable long id , @RequestBody @Valid Recipe recipe) {
        Recipe updated = service.updateRecipe(id, recipe);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        service.deleteByIdRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipe() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllRecipe());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) {
        return service.findByIdRecipe(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/sorted-name-ASC")
    public ResponseEntity<List<Recipe>> getFindAllSortedNameASC() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllSortedNameASC());
    }

    @GetMapping("/sorted-name-DESC")
    public ResponseEntity<List<Recipe>> getFindAllSortedNameDESC() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllSortedNameDESC());
    }

    @GetMapping("/sorted-cooking-time-ASC")
    public ResponseEntity<List<Recipe>> getFindAllSortedCookingTimeASC() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllSortedCookingTimeASC());
    }

    @GetMapping("/sorted-cooking-time-DESC")
    public ResponseEntity<List<Recipe>> getFindAllSortedCookingTimeDESC() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllSortedCookingTimeDESC());
    }

    @PostMapping("/search")
    public ResponseEntity<List<Recipe>> getSearchRecipe(@RequestBody RecipeSearch searchName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAllByNameContainingIgnoreCase(searchName.getName()));
    };
}