package com.example.recipeapp.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM favorite_recipes")
    LiveData<List<RecipeEntity>> getAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(RecipeEntity recipe);

    @Delete
    void deleteRecipe(RecipeEntity recipe);

    @Query("SELECT * FROM favorite_recipes WHERE recipeId = :recipeId LIMIT 1")
    RecipeEntity getRecipeById(String recipeId);
}

