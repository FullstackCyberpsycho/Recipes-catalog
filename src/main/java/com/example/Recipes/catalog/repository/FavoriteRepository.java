package com.example.Recipes.catalog.repository;

import com.example.Recipes.catalog.models.Favorite;
import com.example.Recipes.catalog.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    void deleteByRecipeId(Long recipeId);
    boolean existsByRecipeId(Long recipeId);

    @Query("SELECT f.recipe FROM Favorite f ORDER BY f.recipe.name ASC")
    List<Recipe> findAllSortedNameAsc();

    @Query("SELECT f.recipe FROM Favorite f ORDER BY f.recipe.name DESC")
    List<Recipe> findAllSortedNameDesc();

    @Query("SELECT f.recipe FROM Favorite f ORDER BY f.recipe.cookingTime ASC")
    List<Recipe> findAllSortedCookingTimeAsc();

    @Query("SELECT f.recipe FROM Favorite f ORDER BY f.recipe.cookingTime DESC")
    List<Recipe> findAllSortedCookingTimeDesc();
}
