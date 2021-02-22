package com.example.quickdraw.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.Navigation
import com.example.quickdraw.R

class ActiveGamesAdapter() : RecyclerView.Adapter<ActiveGamesViewHolder>() {

    lateinit var mainFragment: Fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveGamesViewHolder {
        val vh = ActiveGamesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.active_games_item, parent, false))
        return vh
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ActiveGamesViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Navigation.findNavController(mainFragment.requireView()).navigate(R.id.action_mainMenuFragment_to_gameOverviewFragment)
        }
    }

}

class ActiveGamesViewHolder (view: View) : RecyclerView.ViewHolder(view) {



}