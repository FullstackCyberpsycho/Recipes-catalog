package com.example.Recipes.catalog.repository;

import com.example.Recipes.catalog.models.Category;
import com.example.Recipes.catalog.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByNameContainingIgnoreCase(String name);
    //List<Recipe> findAllByCategory(Category category);
}
