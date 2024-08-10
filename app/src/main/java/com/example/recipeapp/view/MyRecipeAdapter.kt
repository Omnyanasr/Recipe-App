package com.example.recipeapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.modules.Meal

class MyRecipeAdapter(val context: Context) : RecyclerView.Adapter<MyRecipeAdapter.MyViewHolder>() {

    private var meals: List<Meal> = listOf()
    var onItemClick: ((Meal) -> Unit)? = null

    private var selectedMeals: MutableSet<Meal> = mutableSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val meal = meals[position]

        holder.recipeTitle.text = meal.strMeal
        Glide.with(context).load(meal.strMealThumb).into(holder.image)

        // Update UI based on whether the meal is selected
        holder.card.alpha = if (selectedMeals.contains(meal)) 0.5f else 1.0f

        holder.card.setOnClickListener {
            onItemClick?.invoke(meal)
        }

        holder.card.setOnLongClickListener {
            if (selectedMeals.contains(meal)) {
                selectedMeals.remove(meal)
                holder.card.alpha = 1.0f
            } else {
                selectedMeals.add(meal)
                holder.card.alpha = 0.5f
            }
            true
        }
    }

    fun setMeals(meals: List<Meal>) {
        this.meals = meals
        selectedMeals.clear()  // Clear selection when setting new data
        notifyDataSetChanged()
    }

    fun selectAllItems() {
        selectedMeals.clear()
        selectedMeals.addAll(meals)
        notifyDataSetChanged()
    }

    fun clearAllSelections() {
        selectedMeals.clear()
        notifyDataSetChanged()
    }

    fun getSelectedMeals(): List<Meal> {
        return selectedMeals.toList()
    }

    fun removeMeal(meal: Meal) {
        meals = meals.filter { it.idMeal != meal.idMeal }
        notifyDataSetChanged()
    }
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.img_recipe)
        val recipeTitle: TextView = view.findViewById(R.id.recipeTitleTxt)
        val card: CardView = view.findViewById(R.id.cv_item)
    }
}
