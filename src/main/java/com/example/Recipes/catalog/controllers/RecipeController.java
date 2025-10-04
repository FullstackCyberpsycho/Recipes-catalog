package com.example.Recipes.catalog.controllers;

import com.example.Recipes.catalog.models.Category;
import com.example.Recipes.catalog.models.Difficulty;
import com.example.Recipes.catalog.models.Recipe;
import com.example.Recipes.catalog.models.RecipeSearch;
import com.example.Recipes.catalog.services.CategoryService;
import com.example.Recipes.catalog.services.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
//import java.util.Optional;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveRecipe(recipe));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable long id , @RequestBody @Valid Recipe recipe) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateRecipe(id, recipe));
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

    @GetMapping("/sortedDifficulty/{difficulty}")
    public ResponseEntity<List<Recipe>> getRecipeByDifficulty(@PathVariable Difficulty difficulty) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findByDifficulty(difficulty));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Recipe>> getRecipeByCategoryId(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findByCategoryId(id));
    }

    @PostMapping("/search")
    public ResponseEntity<List<Recipe>> getSearchRecipe(@RequestBody RecipeSearch searchName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAllByNameContainingIgnoreCase(searchName.getName()));
    };
}