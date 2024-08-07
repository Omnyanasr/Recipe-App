package com.example.recipeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.network.APIClient
import com.example.recipeapp.network.RetrofitService
import com.example.recipeapp.repo.RecipeRepositoryImplementation
import com.example.recipeapp.viewModel.RecipeViewModel
import com.example.recipeapp.viewModel.RecipeViewModelFactory

class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyRecipeAdapter
    lateinit var recipeViewModel : RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recipeList)

        myAdapter = MyRecipeAdapter(requireContext())
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = layoutManager
        getViewModel()
        recipeViewModel.getAllMeals()
        recipeViewModel.listOfMeals?.observe(viewLifecycleOwner, Observer {
            myAdapter.setMeals(it.meals)
        })

        myAdapter.onItemClick = {
            Toast.makeText(requireContext(), "navigate to detail fragment", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getViewModel(){
        val recipeViewModelFactory = RecipeViewModelFactory(
            RecipeRepositoryImplementation(APIClient)
        )
        recipeViewModel = ViewModelProvider(this , recipeViewModelFactory).get(RecipeViewModel::class.java)
    }



}