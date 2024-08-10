package com.example.recipeapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.repo.RecipeRepositoryImplementation
import com.example.recipeapp.network.APIClient
import com.example.recipeapp.viewModel.RecipeViewModel
import com.example.recipeapp.viewModel.RecipeViewModelFactory
import kotlinx.coroutines.*

class SearchFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var adapter: MyRecipeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var searchCriteriaSpinner: Spinner

    private var searchJob: Job? = null

    private var savedQuery: String? = null
    private var savedCriteria: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val repository = RecipeRepositoryImplementation(APIClient)
        val viewModelFactory = RecipeViewModelFactory(repository)
        recipeViewModel = ViewModelProvider(this, viewModelFactory).get(RecipeViewModel::class.java)

        recyclerView = view.findViewById(R.id.recycler_view)
        searchView = view.findViewById(R.id.search_view)
        searchCriteriaSpinner = view.findViewById(R.id.search_criteria_spinner)

        adapter = MyRecipeAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        recipeViewModel.listOfMeals.observe(viewLifecycleOwner, Observer { mealResponse ->
            mealResponse?.meals?.let {
                adapter.setMeals(it)
            } ?: run {
                adapter.setMeals(emptyList())
            }
        })

        // Restore saved state if available
        savedQuery?.let { searchView.setQuery(it, false) }
        savedCriteria?.let {
            val adapter = searchCriteriaSpinner.adapter
            for (i in 0 until adapter.count) {
                if (adapter.getItem(i) == it) {
                    searchCriteriaSpinner.setSelection(i)
                    break
                }
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()

                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(300) // Debounce delay
                    if (!newText.isNullOrBlank()) {
                        val selectedCriteria = searchCriteriaSpinner.selectedItem.toString()
                        when (selectedCriteria) {
                            "Category" -> {
                                recipeViewModel.getAllMealsInCategory(newText)
                            }
                            "Ingredient" -> {
                                recipeViewModel.getMealListByIngredient(newText)
                            }
                            else -> {
                                recipeViewModel.getMealListByTitle(newText)
                            }
                        }
                    } else {
                        recipeViewModel.getAllMeals()
                    }
                }
                return true
            }
        })

        adapter.onItemClick = {
            val navController = Navigation.findNavController(view)
            val action =
                com.example.recipeapp.view.SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(
                    it.strMeal
                )
            navController.navigate(action)
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        savedQuery = searchView.query.toString()
        savedCriteria = searchCriteriaSpinner.selectedItem.toString()
    }
}