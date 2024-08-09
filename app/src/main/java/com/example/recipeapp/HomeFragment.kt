package com.example.recipeapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.db.LocalDataSource
import com.example.recipeapp.network.APIClient
import com.example.recipeapp.network.RetrofitService
import com.example.recipeapp.repo.RecipeRepositoryImplementation
import com.example.recipeapp.viewModel.RecipeViewModel
import com.example.recipeapp.viewModel.RecipeViewModelFactory

class HomeFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyRecipeAdapter
    lateinit var recipeViewModel: RecipeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recipeList)
        myAdapter = MyRecipeAdapter(requireContext())
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        getViewModel()
        recipeViewModel.listOfMeals?.observe(viewLifecycleOwner, Observer {
            myAdapter.setMeals(it.meals)
        })

        recipeViewModel.getAllMeals()

         myAdapter.onItemClick = {
             val navController = Navigation.findNavController(view)
            val action = HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(it.strMeal)
            navController.navigate(action)
        }

    }


    private fun getViewModel() {
        val recipeViewModelFactory = RecipeViewModelFactory(
            RecipeRepositoryImplementation(APIClient)
        )
        recipeViewModel = ViewModelProvider(this, recipeViewModelFactory).get(RecipeViewModel::class.java)
    }
}
