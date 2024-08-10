package com.example.recipeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.db.LocalDataSource
import com.example.recipeapp.network.APIClient
import com.example.recipeapp.repo.RecipeRepositoryImplementation
import com.example.recipeapp.viewModel.RecipeViewModel
import com.example.recipeapp.viewModel.RecipeViewModelFactory

class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyRecipeAdapter
    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var selectAllButton: Button
    private lateinit var removeAllButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }
    override fun onResume() {
        super.onResume()
        (activity as? RecipeActivity)?.setToolbarTitle("Favorites")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.favoriteRecyclerView)
        selectAllButton = view.findViewById(R.id.select_all_button)
        removeAllButton = view.findViewById(R.id.remove_all_button)
        myAdapter = MyRecipeAdapter(requireContext())
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        // Initialize ViewModel
        getViewModel()

        // Observe listOfMeals LiveData to update the UI when data changes
        recipeViewModel.listOfMeals.observe(viewLifecycleOwner, Observer {
            myAdapter.setMeals(it.meals)
        })

        // Fetch the favorite recipes
        recipeViewModel.getAllFavoriteRecipes()

        // Handle item click to navigate to recipe detail
        myAdapter.onItemClick = {
            val navController = Navigation.findNavController(view)
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToRecipeDetailFragment(it.strMeal)
            navController.navigate(action)
        }

        // Handle Select All functionality (optional)
        selectAllButton.setOnClickListener {
            myAdapter.selectAllItems()
        }

        // Handle Remove All functionality
        removeAllButton.setOnClickListener {
            recipeViewModel.deleteAllFavoriteRecipes()
        }
    }

    private fun getViewModel() {
        val recipeRepositoryImplementation =  RecipeRepositoryImplementation(APIClient)
        recipeRepositoryImplementation.localDataSource = LocalDataSource.getInstance(requireContext())!!
        val recipeViewModelFactory = RecipeViewModelFactory(
            recipeRepositoryImplementation
        )
        recipeViewModel = ViewModelProvider(this, recipeViewModelFactory).get(RecipeViewModel::class.java)
    }
}

