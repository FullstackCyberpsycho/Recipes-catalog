package com.example.Recipes.catalog.services;

import com.example.Recipes.catalog.models.Favorite;
import com.example.Recipes.catalog.models.Recipe;
import com.example.Recipes.catalog.repository.FavoriteRepository;
import com.example.Recipes.catalog.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final RecipeRepository recipeRepository;
    private static final Logger log = LoggerFactory.getLogger(FavoriteService.class);

    @CacheEvict(value = "favorite", allEntries = true)
    public void addFavorite(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Рецепт не найден"));

        if (favoriteRepository.existsByRecipeId(recipeId)) {
            throw new RuntimeException("Рецепт уже в избранном");
        }

        Favorite favorite = new Favorite();
        favorite.setRecipe(recipe);
        favoriteRepository.save(favorite);

        log.info("Рецепт '{}' добавлен в избранное", recipe.getName());
    }

    @Cacheable(value = "favorite", key = "'allFavorite'")
    public List<Recipe> getAllFavorites() {
        log.info("Запрошены все избранные рецепты");
        return favoriteRepository.findAll()
                .stream()
                .map(Favorite::getRecipe)
                .toList();
    }

    @Transactional
    @CacheEvict(value = "favorite", allEntries = true)
    public void deleteFavorite(Long recipeId) {
        if (!favoriteRepository.existsByRecipeId(recipeId)) {
            throw new RuntimeException("Рецепт не найден в избранном");
        }

        favoriteRepository.deleteByRecipeId(recipeId);
        log.info("Рецепт с id {} удалён из избранного", recipeId);
    }

    @Cacheable(value = "favorite", key = "'sortedNameAsc'")
    public List<Recipe> findAllSortedNameAsc() {
        log.info("Поиск избранных рецептов отсортированых по name ASC");
        return favoriteRepository.findAllSortedNameAsc();
    }

    @Cacheable(value = "favorite", key = "'sortedNameDesc'")
    public List<Recipe> findAllSortedNameDesc() {
        log.info("Поиск избранных рецептов отсортированых по name DESC");
        return favoriteRepository.findAllSortedNameDesc();
    }

    @Cacheable(value = "favorite", key = "'sortedCookingTimeAsc'")
    public List<Recipe> findAllSortedCookingTimeAsc() {
        log.info("Поиск избранных рецептов по временни готовки отсортированых по ASC");
        return favoriteRepository.findAllSortedCookingTimeAsc();
    }

    @Cacheable(value = "favorite", key = "'sortedCookingTimeDesc'")
    public List<Recipe> findAllSortedCookingTimeDesc() {
        log.info("Поиск избранных рецептов по временни готовки отсортированых по DESC");
        return favoriteRepository.findAllSortedCookingTimeDesc();
    }
}