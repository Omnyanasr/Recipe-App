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
    val listOfCategories: LiveData<CategoryResponse> = _listOfCategories
    val listOfMeals : LiveData<MealResponse> = _listOfMeals

    fun getMealListByTitle(mealTitle: String){
        viewModelScope.launch {
            val result = recipeRepository.getRemoteMealListByTitle(mealTitle)

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
            val listOfCategories = async(Dispatchers.IO) {recipeRepository.getRemoteCategoryList()}.await().body()

            var mealList: MutableList<Meal> = mutableListOf()

            for (category in listOfCategories!!.categories){
                val localListOfMeals = async(Dispatchers.IO) {recipeRepository.getAllRemoteMealsInCategory(category.strCategory)}.await().body()
                mealList.addAll(localListOfMeals!!.meals)
            }

            _listOfMeals.value = MealResponse(mealList)
        }


    }


}