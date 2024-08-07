package com.example.recipeapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.repo.RecipeRepository

class RecipeViewModelFactory(val recipeRepository: RecipeRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeViewModel(recipeRepository) as T
    }
}