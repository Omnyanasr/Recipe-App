package com.example.recipeapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeapp.modules.Meal
import com.example.recipeapp.modules.MealResponse

@Dao
interface RecipeDao {
    @Query("select * from favoritemealrecipes")
    suspend fun getAllData(): LiveData<MealResponse>

    @Insert
    suspend fun insert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)
}