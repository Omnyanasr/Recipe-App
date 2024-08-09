package com.example.recipeapp.db

import androidx.lifecycle.LiveData
import com.example.recipeapp.modules.Meal
import com.example.recipeapp.modules.MealResponse

interface LocalDataSourceInterface {

    suspend fun getAllData() : LiveData<MealResponse>
    suspend fun insertData(meal: Meal)
    suspend fun deleteData(meal: Meal)
}