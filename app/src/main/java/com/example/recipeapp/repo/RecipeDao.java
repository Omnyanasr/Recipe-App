package com.example.recipeapp.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;

@Dao
public interface RecipeDao {

    // Fetch all favorite meals (stored as MealEntity)
    @Query("SELECT * FROM meals")
    LiveData<List<MealEntity>> getAllMeals();

    // Insert a single MealEntity into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(MealEntity meal);

    // Delete a MealEntity from the database
    @Delete
    void deleteMeal(MealEntity meal);

    // Fetch a single meal by its ID
    @Query("SELECT * FROM meals WHERE idMeal = :mealId LIMIT 1")
    MealEntity getMealById(String mealId);

    // Additional queries if needed
}


