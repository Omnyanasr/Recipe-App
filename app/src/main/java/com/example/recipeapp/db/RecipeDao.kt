package com.example.recipeapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeapp.modules.Meal

@Dao
interface RecipeDao {

    // Fetch all favorite meals (stored as Meal)
    @Query("SELECT * FROM favorite_meal_recipes")
    suspend fun getAllFavoriteRecipes(): List<Meal>

    // Insert a single Meal into the database
    @Insert
    suspend fun insert(meal: Meal)

    // Delete a Meal from the database
    @Delete
    suspend fun delete(meal: Meal)

    // Check if a recipe is a favorite by its ID
    @Query("SELECT Count(*) FROM favorite_meal_recipes WHERE idMeal = :recipeId")
    suspend fun isFavoriteRecipe(recipeId: String): Int

    // Delete all favorite meals
    @Query("DELETE FROM favorite_meal_recipes")
    suspend fun deleteAllMeals()
}
