package com.example.Recipes.catalog.repository;

import com.example.Recipes.catalog.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    void deleteByRecipeId(Long recipeId);
    boolean existsByRecipeId(Long recipeId);

//    void deleteByRecipeId(Long recipeId);
//    List<Favorite> findByRecipeId(Long recipeId);
}
