package com.example.recipeapp.repo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_recipes")
public class RecipeEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String recipeId;
    private String name;
    private String imageUrl;

    public RecipeEntity(String recipeId, String name, String imageUrl) {
        this.recipeId = recipeId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

