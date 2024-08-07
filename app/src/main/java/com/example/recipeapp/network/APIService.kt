package com.example.recipeapp.network

import com.example.recipeapp.modules.CategoryResponse
import com.example.recipeapp.modules.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("search.php")
    suspend fun getMealListByTitle(@Query("s") mealTitle: String): Response<MealResponse>

    @GET("categories.php")
    suspend fun getCategoryList(): Response<CategoryResponse>

    @GET("filter.php")
    suspend fun getAllMealsInCategory(@Query("c") category: String): Response<MealResponse>
}