package com.example.recipeapp.repo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey val idMeal: String, // Assuming idMeal is unique
    val strMeal: String,
    val strMealThumb: String,
    val strInstructions: String,
    // Add other fields as needed
)

