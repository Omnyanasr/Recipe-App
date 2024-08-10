package com.example.recipeapp.repo

import com.example.recipeapp.db.LocalDataSource
import com.example.recipeapp.modules.CategoryResponse
import com.example.recipeapp.modules.Meal
import com.example.recipeapp.modules.MealResponse
import com.example.recipeapp.network.RemoteDataSource
import retrofit2.Response

class RecipeRepositoryImplementation(val remoteDataSource: RemoteDataSource) : RecipeRepository {
    lateinit var  localDataSource: LocalDataSource



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

    override suspend fun getAllRemoteMealsByIngredient(ingredient: String): Response<MealResponse> {
        return remoteDataSource.getMealsByIngredient(ingredient)
    }

    override suspend fun getAllFavoriteRecipes(): List<Meal> {
        return localDataSource.getAllFavoriteRecipes()
    }

    override suspend fun insertIntoFavorite(meal: Meal) {
        localDataSource.insert(meal)
    }

    override suspend fun deleteFromFavorite(meal: Meal) {
        localDataSource.delete(meal)
    }

    override suspend fun isFavoriteRecipe(recipeId: String): Int {
        return localDataSource.isFavoriteRecipe(recipeId)
    }
    override suspend fun deleteAllFavoriteRecipes() {
        localDataSource.deleteAllFavoriteRecipes()
    }
}