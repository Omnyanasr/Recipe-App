package com.example.recipeapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recipeapp.modules.Meal
import com.example.recipeapp.network.APIClient
import com.example.recipeapp.repo.RecipeRepositoryImplementation
import com.example.recipeapp.viewModel.RecipeViewModel
import com.example.recipeapp.viewModel.RecipeViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.util.concurrent.Executors


class RecipeDetailFragment : Fragment() {

    lateinit var viewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image : ImageView = view.findViewById(R.id.recipeDetailImage)
        val category : TextView = view.findViewById(R.id.info_category)
        val title : TextView = view.findViewById(R.id.info_title)
        val area : TextView = view.findViewById(R.id.info_area)
        val tags : TextView = view.findViewById(R.id.info_tags)
        val ingrediants : TextView = view.findViewById(R.id.ingrediants)
        val instruction : TextView = view.findViewById(R.id.instructions)
        val fab : FloatingActionButton = view.findViewById(R.id.favoriteBTN)
        val moreInfoBTN : View = view.findViewById(R.id.more_info_include)
        val moreInfoLayout : LinearLayout = view.findViewById(R.id.more_linear)
        val lessInfoBTN : View = view.findViewById(R.id.less_info_include)
        val progressIndicator : CircularProgressIndicator = view.findViewById(R.id.progress_indicator)

        getViewModel()
        viewModel.getAllMeals()
        viewModel.listOfMeals?.observe(viewLifecycleOwner, Observer {
            val meal = it.meals[0]
            Glide.with(requireContext()).load(meal.strMealThumb).into(image)
            category.text = meal.strCategory
            title.text = meal.strMeal
            area.text = meal.strArea
            tags.text = meal.strTags
            instruction.text = meal.strInstructions
            ingrediants.text = getIngerdiantString(meal)

        })

        fab.setOnClickListener {
            Log.d("DEBUG", "onViewCreated: fab.contentDescription = ${fab.contentDescription}")
            Log.d("DEBUG", "onViewCreated: getText(R.string.not_favorite) = ${getText(R.string.not_favorite)}")
            Log.d("DEBUG", "onViewCreated: getText(R.string.favorite) = ${getText(R.string.favorite)}")
            if(fab.contentDescription == getText(R.string.not_favorite)){
                fab.setImageResource(R.drawable.ic_launcher_favorite_foreground)
                fab.contentDescription = getString(R.string.favorite)
            }else{
                fab.setImageResource(R.drawable.ic_launcher_favorite_border_foreground)
                fab.contentDescription = getText(R.string.not_favorite)
            }

        }

        moreInfoBTN.setOnClickListener {
            moreInfoLayout.visibility = View.VISIBLE
            moreInfoBTN.visibility = View.GONE
        }

        lessInfoBTN.setOnClickListener {
            moreInfoLayout.visibility = View.GONE
            moreInfoBTN.visibility = View.VISIBLE
        }

    }

    override fun onStart() {
        super.onStart()
        val executorService = Executors.newSingleThreadExecutor()
        var layout : ScrollView = requireView().findViewById(R.id.scrollView)
        val handler = Handler(Looper.getMainLooper())
        val progressIndicator : CircularProgressIndicator = requireView().findViewById(R.id.progress_indicator)
        val catTXT : TextView = requireView().findViewById(R.id.info_category)
        executorService.execute(Runnable {
            layout.visibility = View.GONE
            progressIndicator.show()

            while (catTXT.text == "TextView")
            {
                SystemClock.sleep(1000)
            }

            handler.postDelayed(Runnable{
                progressIndicator.hide()
                layout.visibility = View.VISIBLE
            },100)
        })
    }

    private fun getViewModel(){
        val recipeViewModelFactory = RecipeViewModelFactory(
            RecipeRepositoryImplementation(APIClient)
        )
        viewModel = ViewModelProvider(this , recipeViewModelFactory).get(RecipeViewModel::class.java)
    }

    private fun getIngerdiantString(meal: Meal): String{
        val listOfIngrediants = listOf<String>(meal.strIngredient1, meal.strIngredient2, meal.strIngredient3, meal.strIngredient4,
                    meal.strIngredient5, meal.strIngredient6, meal.strIngredient7, meal.strIngredient8, meal.strIngredient9,
                    meal.strIngredient10, meal.strIngredient11, meal.strIngredient12,meal.strIngredient13, meal.strIngredient14,
                    meal.strIngredient15,meal.strIngredient16, meal.strIngredient17,meal.strIngredient18,meal.strIngredient19,meal.strIngredient20)

        val listOfMeasures = listOf<String>(meal.strMeasure1,meal.strMeasure2, meal.strMeasure3, meal.strMeasure4, meal.strMeasure5, meal.strMeasure6, meal.strMeasure7, meal.strMeasure8, meal.strMeasure9, meal.strMeasure10,meal.strMeasure12,
            meal.strMeasure13, meal.strMeasure14, meal.strMeasure15, meal.strMeasure16, meal.strMeasure17, meal.strMeasure18, meal.strMeasure19, meal.strMeasure20)

        var finalString : String = ""

        for(i in 1..20)
        {
            if(listOfIngrediants[i].isNullOrEmpty()){
                break
            }

            finalString += "${listOfMeasures[i]}  ${listOfIngrediants[i]}\n\n"
        }

        return finalString
    }



}