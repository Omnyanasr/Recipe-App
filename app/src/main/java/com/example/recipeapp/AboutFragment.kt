package com.example.recipeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView

class AboutFragment : Fragment() {

    private lateinit var creatorsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        creatorsRecyclerView = view.findViewById(R.id.creators_recycler_view)

        val creators = listOf(
            Pair("Omnya Nasr", R.drawable.admins),
            Pair("Mariam Ali", R.drawable.admins),
            Pair("Radwa Mohammed", R.drawable.admins),
            Pair("Fatima Ashraf", R.drawable.admins),
            Pair("Sarah Mohammed", R.drawable.admins)
        )

        val adapter = CreatorAdapter(creators)
        creatorsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        creatorsRecyclerView.adapter = adapter
    }
}

class CreatorAdapter(private val creators: List<Pair<String, Int>>) : RecyclerView.Adapter<CreatorAdapter.CreatorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_creator, parent, false)
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
