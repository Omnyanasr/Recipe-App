package com.example.recipeapp.network

import com.example.recipeapp.modules.CategoryResponse
import com.example.recipeapp.modules.MealResponse
import retrofit2.Response
import retrofit2.http.Query

interface RemoteDataSource {

    suspend fun getCategoryList(): Response<CategoryResponse>
    suspend fun getMealListByTitle(mealTitle: String): Response<MealResponse>
    suspend fun getAllMealsInCategory(category: String): Response<MealResponse>
    suspend fun getAllMealsByFirstLetter(mealFirstLetter: Char): Response<MealResponse>
}