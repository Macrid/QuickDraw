package com.example.quickdraw

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.Navigation

class FriendlistSearchAdapter() : RecyclerView.Adapter<FriendlistViewHolder>() {

    lateinit var mainFragment: Fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendlistViewHolder {
        val vh = FriendlistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.active_games_item, parent, false))
        return vh
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: FriendlistViewHolder, position: Int) {
        holder.itemView.setOnClickListener {

        }
    }

}

class FriendlistSearchViewHolder (view: View) : RecyclerView.ViewHolder(view) {



}