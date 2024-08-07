package com.example.recipeapp.repo

import com.example.recipeapp.modules.CategoryResponse
import com.example.recipeapp.modules.MealResponse
import com.example.recipeapp.network.RemoteDataSource
import retrofit2.Response

class RecipeRepositoryImplementation(val remoteDataSource: RemoteDataSource) : RecipeRepository {
    override suspend fun getRemoteCategoryList(): Response<CategoryResponse> {
        return remoteDataSource.getCategoryList()
    }

    override suspend fun getRemoteMealListByTitle(mealTitle: String): Response<MealResponse> {
        return remoteDataSource.getMealListByTitle(mealTitle)
    }

    override suspend fun getAllRemoteMealsInCategory(category: String): Response<MealResponse> {
        return remoteDataSource.getAllMealsInCategory(category)
    }

    override suspend fun getAllRemoteMealsByFirstLetter(mealFirstLetter: Char): Response<MealResponse> {
        return remoteDataSource.getAllMealsByFirstLetter(mealFirstLetter)
    }

}