package com.example.Recipes.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RecipesCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipesCatalogApplication.class, args);
	}

}
