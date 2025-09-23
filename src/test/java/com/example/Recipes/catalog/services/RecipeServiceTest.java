package com.example.Recipes.catalog.services;

import com.example.Recipes.catalog.models.Recipe;
import com.example.Recipes.catalog.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
    @Mock
    private RecipeRepository repository;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    void testSaveRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Борщ");

        when(repository.save(any(Recipe.class))).thenReturn(recipe);

        Recipe saved = recipeService.saveRecipe(recipe);

        assertEquals("Борщ", saved.getName());
        verify(repository).save(recipe);
    }
}
