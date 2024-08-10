package com.example.recipeapp.network

import android.util.Log
import com.example.recipeapp.modules.CategoryResponse
import com.example.recipeapp.modules.MealResponse
import retrofit2.Response

object APIClient : RemoteDataSource {
    override suspend fun getCategoryList(): Response<CategoryResponse> {
        return RetrofitService.retrofit.create(APIService::class.java).getCategoryList()
    }

    override suspend fun getMealListByTitle(mealTitle: String): Response<MealResponse> {
        return RetrofitService.retrofit.create(APIService::class.java).getMealListByTitle(mealTitle)
    }

    override suspend fun getAllMealsInCategory(category: String): Response<MealResponse> {
        return RetrofitService.retrofit.create(APIService::class.java).getAllMealsInCategory(category)
    }

    override suspend fun getAllMealsByFirstLetter(mealFirstLetter: Char): Response<MealResponse> {
        return RetrofitService.retrofit.create(APIService::class.java).getAllMealsByFirstLetter(mealFirstLetter)
    }

    override suspend fun getMealsByIngredient(ingredient: String): Response<MealResponse> {
        return RetrofitService.retrofit.create(APIService::class.java).getMealsByIngredient(ingredient)
    }
}