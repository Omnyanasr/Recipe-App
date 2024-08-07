package com.example.recipeapp.repo

import com.example.recipeapp.modules.CategoryResponse
import com.example.recipeapp.modules.MealResponse
import retrofit2.Response

interface RecipeRepository {
    suspend fun getRemoteCategoryList(): Response<CategoryResponse>
    suspend fun getRemoteMealListByTitle(mealTitle: String): Response<MealResponse>
    suspend fun getAllRemoteMealsInCategory(category: String): Response<MealResponse>
}