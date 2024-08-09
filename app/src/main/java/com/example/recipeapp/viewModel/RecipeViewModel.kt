package com.example.recipeapp.viewModel

import android.util.Log
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

class RecipeViewModel(val recipeRepository: RecipeRepository) : ViewModel() {
    private val _listOfMeals = MutableLiveData<MealResponse>()
    private var _listOfCategories = MutableLiveData<CategoryResponse>()
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean> = _isFavorite
    val listOfMeals : LiveData<MealResponse> = _listOfMeals

    fun getMealListByTitle(mealTitle: String){
        viewModelScope.launch {
            val result = recipeRepository.getRemoteMealListByTitle(mealTitle)

            if(result.isSuccessful){
                _listOfMeals.value = result.body()
            }
        }
    }

    fun getMealsByFirstLetter(firstLetter: Char){
        viewModelScope.launch {
            val result = recipeRepository.getAllRemoteMealsByFirstLetter(firstLetter)

            if(result.isSuccessful){
                _listOfMeals.value = result.body()
            }
        }
    }

    fun getAllMealsInCategory(category: String){
        viewModelScope.launch {
            val result = recipeRepository.getAllRemoteMealsInCategory(category)

            if(result.isSuccessful){
                _listOfMeals.value = result.body()
            }
        }
    }

    fun getAllCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = recipeRepository.getRemoteCategoryList()

            if(result.isSuccessful){
                _listOfCategories.value = result.body()

            }
        }
    }

    fun getAllMeals() {
        viewModelScope.launch() {
            val listOfLiters = listOf<Char>('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z')

            var mealList: MutableList<Meal> = mutableListOf()

            for (letter in listOfLiters){
                val localListOfMeals = async(Dispatchers.IO) {recipeRepository.getAllRemoteMealsByFirstLetter(letter)}.await().body()
                if (localListOfMeals!!.meals != null)
                {
                    mealList.addAll(localListOfMeals!!.meals)
                }

            }

            _listOfMeals.value = MealResponse(mealList.shuffled())
        }


    }

    fun getAllFavoriteRecipes(){
        viewModelScope.launch() {
            _listOfMeals.value = MealResponse(recipeRepository.getAllFavoriteRecipes())
        }
    }

    fun insertIntoFavoriteRecipe(meal: Meal){
        viewModelScope.launch() {
            recipeRepository.insertIntoFavorite(meal)
        }
    }

    fun deleteFromFavoriteRecipe(meal: Meal){
        viewModelScope.launch() {
            recipeRepository.deleteFromFavorite(meal)
        }
    }

    fun isFavoriteRecipe(recipeId: String){
        viewModelScope.launch {
            _isFavorite.value = recipeRepository.isFavoriteRecipe(recipeId) > 0
        }
    }

}