package com.example.recipeapp.about

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R


class CreatorAdapter(private val creators: List<Pair<String, Int>>) : RecyclerView.Adapter<CreatorAdapter.CreatorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_creator_about, parent, false)
        return CreatorViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreatorViewHolder, position: Int) {
        val creator = creators[position]
        holder.bind(creator)
    }

    override fun getItemCount() = creators.size

    inner class CreatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val creatorName: TextView = itemView.findViewById(R.id.creator_name)
        private val creatorImage: ImageView = itemView.findViewById(R.id.creator_image)

        fun bind(creator: Pair<String, Int>) {
            creatorName.text = creator.first
            creatorImage.setImageResource(creator.second)
        }
    }
}
