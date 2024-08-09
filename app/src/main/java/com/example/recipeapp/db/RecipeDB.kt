package com.example.recipeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeapp.modules.Meal

@Database(entities = [Meal::class], version = 1)
abstract class RecipeDB : RoomDatabase(){
    abstract fun getRecipeDao(): RecipeDao

    companion object {
        private var instance: RecipeDB? = null

        @Synchronized
        fun getInstance(context: Context): RecipeDB {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, RecipeDB::class.java, "recipe.db").build()
                }
            }

            return instance!!
        }
    }
}