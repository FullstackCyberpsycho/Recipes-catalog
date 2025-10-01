package com.example.Recipes.catalog.repository;

import com.example.Recipes.catalog.models.Difficulty;
import com.example.Recipes.catalog.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByNameContainingIgnoreCase(String name);
    List<Recipe> findByDifficulty(Difficulty difficulty);
    List<Recipe> findByCategoryId(Long categoryId);
}
