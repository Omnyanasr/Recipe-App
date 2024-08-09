package com.example.recipeapp.db

import android.content.Context
import androidx.lifecycle.LiveData
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

    companion object{
        private var instance: LocalDataSource? = null

        fun getInstance(context: Context): LocalDataSource? {
            if (instance == null) {
                instance = LocalDataSource(context)
            }
            return instance as LocalDataSource
        }
    }

    override suspend fun getAllData(): LiveData<MealResponse> {
        return recipeDao.getAllData()
    }

    override suspend fun insertData(meal: Meal) {
        recipeDao.insert(meal)
    }

    override suspend fun deleteData(meal: Meal) {
        recipeDao.delete(meal)
    }


}