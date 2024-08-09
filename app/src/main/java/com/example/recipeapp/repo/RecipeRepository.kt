package com.example.recipeapp.repo

import com.example.recipeapp.modules.CategoryResponse
import com.example.recipeapp.modules.Meal
import com.example.recipeapp.modules.MealResponse
import retrofit2.Response

interface RecipeRepository {
    suspend fun getRemoteCategoryList(): Response<CategoryResponse>
    suspend fun getRemoteMealListByTitle(mealTitle: String): Response<MealResponse>
    suspend fun getAllRemoteMealsInCategory(category: String): Response<MealResponse>
    suspend fun getAllRemoteMealsByFirstLetter(mealFirstLetter: Char): Response<MealResponse>
    suspend fun getAllFavoriteRecipes() : List<Meal>
    suspend fun insertIntoFavorite(meal: Meal)
    suspend fun deleteFromFavorite(meal: Meal)
    suspend fun isFavoriteRecipe(recipeId: String): Int
}