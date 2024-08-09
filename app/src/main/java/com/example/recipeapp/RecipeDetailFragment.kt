package com.example.recipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recipeapp.db.LocalDataSource
import com.example.recipeapp.modules.Meal
import com.example.recipeapp.network.APIClient
import com.example.recipeapp.repo.RecipeRepositoryImplementation
import com.example.recipeapp.viewModel.RecipeViewModel
import com.example.recipeapp.viewModel.RecipeViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import android.widget.Toast
import android.net.Uri
import android.content.Intent

class RecipeDetailFragment : Fragment() {

    val args: RecipeDetailFragmentArgs by navArgs()
    val args : RecipeDetailFragmentArgs by navArgs()
    lateinit var currentMeal : Meal
    lateinit var viewModel: RecipeViewModel

    private lateinit var youtubeWebView: WebView
    private lateinit var playVideoButton: Button

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
        val moreInfoBTN : View = view.findViewById(R.id.more_info_include)
        val moreInfoLayout : LinearLayout = view.findViewById(R.id.more_linear)
        val lessInfoBTN : View = view.findViewById(R.id.less_info_include)

        val image: ImageView = view.findViewById(R.id.recipeDetailImage)
        val category: TextView = view.findViewById(R.id.info_category)
        val title: TextView = view.findViewById(R.id.info_title)
        val area: TextView = view.findViewById(R.id.info_area)
        val tags: TextView = view.findViewById(R.id.info_tags)
        val ingrediants: TextView = view.findViewById(R.id.ingrediants)
        val instruction: TextView = view.findViewById(R.id.instructions)
        val fab: FloatingActionButton = view.findViewById(R.id.favoriteBTN)
        val moreInfoBTN: View = view.findViewById(R.id.more_info_include)
        val moreInfoLayout: LinearLayout = view.findViewById(R.id.more_linear)
        val lessInfoBTN: View = view.findViewById(R.id.less_info_include)
        val progressIndicator: CircularProgressIndicator = view.findViewById(R.id.progress_indicator)

        // Initialize WebView for YouTube video
        youtubeWebView = view.findViewById(R.id.youtube_webview)
        playVideoButton = view.findViewById(R.id.play_video_button)

        getViewModel()
        viewModel.listOfMeals.observe(viewLifecycleOwner, {
            currentMeal = it.meals[0]

            Glide.with(requireContext()).load(currentMeal.strMealThumb).into(image)
            category.text = currentMeal.strCategory
            title.text = currentMeal.strMeal
            area.text = currentMeal.strArea
            tags.text = currentMeal.strTags
            instruction.text = currentMeal.strInstructions
            ingrediants.text = getIngerdiantString(currentMeal)
            changeFabImage(view)
        })
        viewModel.getMealListByTitle(args.mealTitle)

        viewModel.listOfMeals.observe(viewLifecycleOwner, { response ->
            progressIndicator.visibility = View.GONE // Hide progress indicator
            response?.meals?.firstOrNull()?.let { meal ->
                Glide.with(requireContext()).load(meal.strMealThumb).into(image)
                category.text = meal.strCategory
                title.text = meal.strMeal
                area.text = meal.strArea
                tags.text = meal.strTags
                instruction.text = meal.strInstructions
                ingrediants.text = getIngerdiantString(meal)

                // Handle play video button click
                playVideoButton.setOnClickListener {
                    playVideo(meal.strYoutube ?: "")
                }
            }
        })

        fab.setOnClickListener {
            if (fab.contentDescription == getText(R.string.not_favorite)) {
                fab.setImageResource(R.drawable.ic_launcher_favorite_foreground)
                fab.contentDescription = getString(R.string.favorite)
            } else {
                fab.setImageResource(R.drawable.ic_launcher_favorite_border_foreground)
                fab.contentDescription = getText(R.string.not_favorite)
            }
        }
        viewModel.getMealListByTitle(args.mealTitle)

        moreInfoBTN.setOnClickListener {
            moreInfoLayout.visibility = View.VISIBLE
            moreInfoBTN.visibility = View.GONE
        }

        lessInfoBTN.setOnClickListener {
            moreInfoLayout.visibility = View.GONE
            moreInfoBTN.visibility = View.VISIBLE
        }
    }
    private fun playVideo(youtubeUrl: String) {
        if (youtubeUrl.isNotEmpty()) {
            val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
            startActivity(youtubeIntent)
        } else {
            Toast.makeText(requireContext(), "Invalid video URL", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getViewModel() {
        val recipeViewModelFactory = RecipeViewModelFactory(
            recipeRepositoryImplementation
        )
        viewModel = ViewModelProvider(this, recipeViewModelFactory).get(RecipeViewModel::class.java)
    }

    private fun getIngerdiantString(meal: Meal): String {
        val listOfIngrediants = listOf(
            meal.strIngredient1, meal.strIngredient2, meal.strIngredient3, meal.strIngredient4,
            meal.strIngredient5, meal.strIngredient6, meal.strIngredient7, meal.strIngredient8, meal.strIngredient9,
            meal.strIngredient10, meal.strIngredient11, meal.strIngredient12, meal.strIngredient13, meal.strIngredient14,
            meal.strIngredient15, meal.strIngredient16, meal.strIngredient17, meal.strIngredient18, meal.strIngredient19, meal.strIngredient20
        )
    private fun getIngerdiantString(meal: Meal): String{
        val listOfIngrediants = listOf<String?>(meal.strIngredient1, meal.strIngredient2, meal.strIngredient3, meal.strIngredient4,
                    meal.strIngredient5, meal.strIngredient6, meal.strIngredient7, meal.strIngredient8, meal.strIngredient9,
                    meal.strIngredient10, meal.strIngredient11, meal.strIngredient12,meal.strIngredient13, meal.strIngredient14,
                    meal.strIngredient15,meal.strIngredient16, meal.strIngredient17,meal.strIngredient18,meal.strIngredient19,meal.strIngredient20)

        val listOfMeasures = listOf(
            meal.strMeasure1, meal.strMeasure2, meal.strMeasure3, meal.strMeasure4, meal.strMeasure5, meal.strMeasure6,
            meal.strMeasure7, meal.strMeasure8, meal.strMeasure9, meal.strMeasure10, meal.strMeasure11, meal.strMeasure12,
            meal.strMeasure13, meal.strMeasure14, meal.strMeasure15, meal.strMeasure16, meal.strMeasure17, meal.strMeasure18,
            meal.strMeasure19, meal.strMeasure20
        )
        val listOfMeasures = listOf<String?>(meal.strMeasure1,meal.strMeasure2, meal.strMeasure3, meal.strMeasure4, meal.strMeasure5, meal.strMeasure6, meal.strMeasure7, meal.strMeasure8, meal.strMeasure9, meal.strMeasure10, meal.strMeasure11,meal.strMeasure12,
            meal.strMeasure13, meal.strMeasure14, meal.strMeasure15, meal.strMeasure16, meal.strMeasure17, meal.strMeasure18, meal.strMeasure19, meal.strMeasure20)

        val stringBuilder = StringBuilder()

        for (i in listOfIngrediants.indices) {
            if (!listOfIngrediants[i].isNullOrEmpty()) {
                stringBuilder.append("${listOfMeasures[i] ?: ""}  ${listOfIngrediants[i]}\n\n")
            }
        }

        return stringBuilder.toString()
    }

    override fun onStop() {
        super.onStop()
        // Clean up the WebView
        youtubeWebView.loadUrl("about:blank")
    }
}

    fun changeFabImage(view: View)
    {
        val fab : FloatingActionButton = view.findViewById(R.id.favoriteBTN)
        viewModel.isFavorite.observe(viewLifecycleOwner, {isFavorite ->
            if(isFavorite){
                fab.setImageResource(R.drawable.ic_launcher_favorite_foreground)
            }else{
                fab.setImageResource(R.drawable.ic_launcher_favorite_border_foreground)
            }
        })


        fab.setOnClickListener {
            if(viewModel.isFavorite.value == false){
                viewModel.insertIntoFavoriteRecipe(currentMeal)
                fab.setImageResource(R.drawable.ic_launcher_favorite_foreground)
//                fab.contentDescription = getString(R.string.favorite)
            }else{
                viewModel.deleteFromFavoriteRecipe(currentMeal)
                fab.setImageResource(R.drawable.ic_launcher_favorite_border_foreground)
//                fab.contentDescription = getText(R.string.not_favorite)
            }

        }

        viewModel.isFavoriteRecipe(currentMeal.idMeal)
    }










