package com.example.Recipes.catalog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "recipes")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Это поле не должно быть пустым!")
    @Size(max = 200, message = "Это поле не должно превышать 200 символов")
    private String name;

    private String description;

    @NotBlank(message = "Это поле не должно быть пустым!")
    private String ingredients;

    @NotBlank(message = "Это поле не должно быть пустым!")
    private String instructions;

    @Min(value = 0, message = "Это поле не должно быть отрицательным!")
    @Column(name = "cooking_time")
    private Integer cookingTime;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty = Difficulty.UNKNOWN;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;
}