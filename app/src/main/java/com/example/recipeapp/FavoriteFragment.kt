package com.example.recipeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.db.LocalDataSource
import com.example.recipeapp.modules.Meal
import com.example.recipeapp.network.APIClient
import com.example.recipeapp.repo.RecipeRepositoryImplementation
import com.example.recipeapp.viewModel.RecipeViewModel
import com.example.recipeapp.viewModel.RecipeViewModelFactory


class FavoriteFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyRecipeAdapter
    lateinit var recipeViewModel: RecipeViewModel

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.favoriteRecyclerView)
        myAdapter = MyRecipeAdapter(requireContext())
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        getViewModel()

        recipeViewModel.listOfMeals?.observe(viewLifecycleOwner, Observer {
            myAdapter.setMeals(it.meals)
        })
        recipeViewModel.getAllFavoriteRecipes()

        myAdapter.onItemClick = {
            val navController = Navigation.findNavController(view)
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToRecipeDetailFragment(it.strMeal)
            navController.navigate(action)
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