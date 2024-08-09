package com.example.recipeapp.db

import androidx.lifecycle.LiveData
import com.example.recipeapp.modules.Meal
import com.example.recipeapp.modules.MealResponse

interface LocalDataSourceInterface {

    suspend fun getAllFavoriteRecipes() : List<Meal>
    suspend fun insert(meal: Meal)
    suspend fun delete(meal: Meal)
    suspend fun isFavoriteRecipe(recipeId: String): Int
}