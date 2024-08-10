package com.example.recipeapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.modules.CategoryResponse
import com.example.recipeapp.modules.Meal
import com.example.recipeapp.modules.MealResponse
import com.example.recipeapp.repo.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    private val _listOfMeals = MutableLiveData<MealResponse>()
    private val _listOfCategories = MutableLiveData<CategoryResponse>()
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite
    val listOfMeals: LiveData<MealResponse> = _listOfMeals

    private var lastQuery: String? = null
    private var lastCriteria: String? = null

    // Get meals by title, avoiding redundant network calls
    fun getMealListByTitle(mealTitle: String) {
        if (mealTitle == lastQuery && lastCriteria == "Title") return

        lastQuery = mealTitle
        lastCriteria = "Title"

        viewModelScope.launch {
            val result = recipeRepository.getRemoteMealListByTitle(mealTitle)

            if (result.isSuccessful) {
                _listOfMeals.value = result.body()
            }
        }
    }

    // Get meals by first letter, avoiding redundant network calls
    fun getMealsByFirstLetter(firstLetter: Char) {
        if (firstLetter.toString() == lastQuery && lastCriteria == "FirstLetter") return

        lastQuery = firstLetter.toString()
        lastCriteria = "FirstLetter"

        viewModelScope.launch {
            val result = recipeRepository.getAllRemoteMealsByFirstLetter(firstLetter)

            if (result.isSuccessful) {
                _listOfMeals.value = result.body()
            }
        }
    }

    // Get meals in a category, avoiding redundant network calls
    fun getAllMealsInCategory(category: String) {
        if (category == lastQuery && lastCriteria == "Category") return

        lastQuery = category
        lastCriteria = "Category"

        viewModelScope.launch {
            val result = recipeRepository.getAllRemoteMealsInCategory(category)

            if (result.isSuccessful) {
                _listOfMeals.value = result.body()
            }
        }
    }

    // Get all categories
    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = recipeRepository.getRemoteCategoryList()

            if (result.isSuccessful) {
                _listOfCategories.postValue(result.body())
            }
        }
    }

    // Fetch all meals by iterating over each letter
    fun getAllMeals() {
        viewModelScope.launch {
            val listOfLetters = ('a'..'z').toList()
            val mealList = mutableListOf<Meal>()

            for (letter in listOfLetters) {
                val localListOfMeals = async(Dispatchers.IO) {
                    recipeRepository.getAllRemoteMealsByFirstLetter(letter)
                }.await().body()

                localListOfMeals?.meals?.let {
                    mealList.addAll(it)
                }
            }

            _listOfMeals.value = MealResponse(mealList.shuffled())
        }
    }

    // Get all favorite recipes
    fun getAllFavoriteRecipes() {
        viewModelScope.launch {
            _listOfMeals.value = MealResponse(recipeRepository.getAllFavoriteRecipes())
        }
    }

    // Insert a recipe into favorites
    fun insertIntoFavoriteRecipe(meal: Meal) {
        viewModelScope.launch {
            recipeRepository.insertIntoFavorite(meal)
            _isFavorite.value = true
        }
    }

    // Delete a recipe from favorites
    fun deleteFromFavoriteRecipe(meal: Meal) {
        viewModelScope.launch {
            recipeRepository.deleteFromFavorite(meal)
            _isFavorite.value = false
        }
    }

    // Delete all favorite recipes
    fun deleteAllFavoriteRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            recipeRepository.deleteAllFavoriteRecipes()
            _listOfMeals.postValue(MealResponse(recipeRepository.getAllFavoriteRecipes()))
        }
    }

    // Check if a recipe is a favorite
    fun isFavoriteRecipe(recipeId: String) {
        viewModelScope.launch {
            _isFavorite.value = recipeRepository.isFavoriteRecipe(recipeId) > 0
        }
    }

    // Get meals by ingredient, avoiding redundant network calls
    fun getMealListByIngredient(ingredient: String) {
        if (ingredient == lastQuery && lastCriteria == "Ingredient") return

        lastQuery = ingredient
        lastCriteria = "Ingredient"

        viewModelScope.launch {
            val result = recipeRepository.getAllRemoteMealsByIngredient(ingredient)

            if (result.isSuccessful) {
                _listOfMeals.value = result.body()
            }
        }
    }
}
