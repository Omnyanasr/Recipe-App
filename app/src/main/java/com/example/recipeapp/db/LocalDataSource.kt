package com.example.recipeapp.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.recipeapp.db.LocalDataSourceInterface
import com.example.recipeapp.modules.Meal
import com.example.recipeapp.modules.MealResponse

class LocalDataSource private constructor(context: Context) : LocalDataSourceInterface {

    private lateinit var recipeDao: RecipeDao

    init {
        val recipeDB = RecipeDB.getInstance(context)
        if (recipeDB != null) {
            recipeDao = recipeDB.getRecipeDao()
        }
    }

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(context: Context): LocalDataSource? {
            if (instance == null) {
                instance = LocalDataSource(context)
            }
            return instance as LocalDataSource
        }
    }

    override suspend fun getAllFavoriteRecipes(): List<Meal> {
        return recipeDao.getAllFavoriteRecipes()
    }

    override suspend fun insert(meal: Meal) {
        recipeDao.insert(meal)
    }

    override suspend fun delete(meal: Meal) {
        recipeDao.delete(meal)
    }

    override suspend fun isFavoriteRecipe(recipeId: String): Int {
        return recipeDao.isFavoriteRecipe(recipeId)
    }

    // Method to delete all favorite recipes
    suspend fun deleteAllFavoriteRecipes() {
        recipeDao.deleteAllMeals()
    }
}
