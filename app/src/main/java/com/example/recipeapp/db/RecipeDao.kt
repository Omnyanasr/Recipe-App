package com.example.recipeapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeapp.modules.Meal

@Dao
interface RecipeDao {
    @Query("select * from favorite_meal_recipes")
    suspend fun getAllFavoriteRecipes(): List<Meal>

    @Insert
    suspend fun insert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)
    
    @Query("SELECT Count(*) FROM favorite_meal_recipes WHERE idMeal = :recipeId")
    suspend fun isFavoriteRecipe(recipeId: String): Int
}