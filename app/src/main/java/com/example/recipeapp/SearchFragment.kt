package com.example.recipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var searchJob: Job? = null

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

        recipeViewModel.getAllMeals()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()

                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(300) // Debounce delay
                    if (!newText.isNullOrBlank()) {
                        recipeViewModel.getMealListByTitle(newText)
                    } else {
                        recipeViewModel.getAllMeals()
                    }
                }
                return true
            }
        })

        adapter.onItemClick = {
            val navController = Navigation.findNavController(view)
            val action = SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment(it.strMeal)
            navController.navigate(action)
        }
        return view
    }
}
