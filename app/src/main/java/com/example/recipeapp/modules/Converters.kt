package com.example.recipeapp.modules

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromMealList(mealList: List<Meal>?): String? {
        return if (mealList == null) null else Gson().toJson(mealList)
    }

    @TypeConverter
    fun toMealList(mealString: String?): List<Meal>? {
        return if (mealString == null) null else {
            val listType = object : TypeToken<List<Meal>>() {}.type
            Gson().fromJson(mealString, listType)
        }
    }
}
