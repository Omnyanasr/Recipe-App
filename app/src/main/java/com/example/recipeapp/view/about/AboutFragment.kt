package com.example.recipeapp.view.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R

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
