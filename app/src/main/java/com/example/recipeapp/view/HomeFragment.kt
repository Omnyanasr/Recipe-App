package com.example.recipeapp.view

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
import com.example.recipeapp.R
import com.example.recipeapp.network.APIClient
import com.example.recipeapp.repo.RecipeRepositoryImplementation
import com.example.recipeapp.viewModel.RecipeViewModel
import com.example.recipeapp.viewModel.RecipeViewModelFactory
import com.google.android.material.progressindicator.CircularProgressIndicator

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
    override fun onResume() {
        super.onResume()
        (activity as? RecipeActivity)?.setToolbarTitle("Home")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recipeList)
        myAdapter = MyRecipeAdapter(requireContext())
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val progressIndicator : CircularProgressIndicator = view.findViewById(R.id.home_progress_indicator)

        getViewModel()
        recipeViewModel.listOfMeals?.observe(viewLifecycleOwner, Observer {
            progressIndicator.visibility = View.GONE
            myAdapter.setMeals(it.meals)
        })

        recipeViewModel.getAllMeals()

         myAdapter.onItemClick = {
             val navController = Navigation.findNavController(view)
            val action =
                com.example.recipeapp.view.HomeFragmentDirections.actionHomeFragmentToRecipeDetailFragment(
                    it.strMeal
                )
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
