package com.example.recipeapp

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.modules.Meal

class MyRecipeAdapter(val context: Context) : RecyclerView.Adapter<MyRecipeAdapter.MyViewHolder>() {

    private var meals : List<Meal> = listOf<Meal>()
    var onItemClick : ((Meal) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.recipeTitle.text = meals.get(position)?.strMeal
        Glide.with(context).load(meals.get(position)?.strMealThumb).into(holder.image)

        //click listener used to navigate to recipe detail
        /*
            TO USE: replace any {}

                myRecipeAdapter.onItemClick = {
                    val navController = Navigation.findNavController(view)
                    val action : NavDirections = {fragment class name}Directions.action{from fragment class name}FragmentTo{to fragment class name}}Fragment({arguments})
                    navController.navigate(action)
                }

         */
        holder.card.setOnClickListener{
            onItemClick?.invoke(meals?.get(position)!!)
        }
    }

    fun setMeals(meals: List<Meal>) {
        this.meals = meals
        notifyDataSetChanged()
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image : ImageView= view.findViewById(R.id.img_recipe)
        val recipeTitle : TextView = view.findViewById(R.id.recipeTitleTxt)
        val card : CardView = view.findViewById(R.id.cv_item)
    }

}