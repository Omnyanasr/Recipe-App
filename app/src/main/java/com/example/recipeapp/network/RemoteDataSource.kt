package com.example.recipeapp.network

import com.example.recipeapp.modules.CategoryResponse
import com.example.recipeapp.modules.MealResponse
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getCategoryList(): Response<CategoryResponse>
    suspend fun getMealListByTitle(mealTitle: String): Response<MealResponse>
    suspend fun getAllMealsInCategory(category: String): Response<MealResponse>
}