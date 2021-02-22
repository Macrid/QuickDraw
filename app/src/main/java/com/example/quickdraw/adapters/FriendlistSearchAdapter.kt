package com.example.quickdraw.adapters

import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.quickdraw.R
import com.example.quickdraw.fragments.FriendlistFragment

class FriendlistSearchAdapter() : RecyclerView.Adapter<FriendlistSearchViewHolder>() {

    lateinit var mainFragment: FriendlistFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendlistSearchViewHolder {
        val vh = FriendlistSearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.friend_search_item, parent, false))
        return vh
    }

    override fun getItemCount(): Int {
        mainFragment.friendlistVM.getFilteredUsers().size.let{
            return it
            Log.w("Debug",it.toString())
        }
        return 0
    }

    override fun onBindViewHolder(holder: FriendlistSearchViewHolder, position: Int) {
        var currentUser = mainFragment.friendlistVM.getFilteredUsers()[position]
        holder.displayNameTextView.text = currentUser.displayName
        holder.userIDTextView.text = currentUser.ID
        holder.addButton.setOnClickListener {
            mainFragment.friendlistVM.addFriend(mainFragment.friendlistVM.getFilteredUsers()[position].ID!!)
        }
    }

}

class FriendlistSearchViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    var displayNameTextView = view.findViewById<TextView>(R.id.friendSearchItemDisplayName)
    var userIDTextView = view.findViewById<TextView>(R.id.friendSearchItemUID)
    var addButton = view.findViewById<TextView>(R.id.friendSearchItemAddButton)
}