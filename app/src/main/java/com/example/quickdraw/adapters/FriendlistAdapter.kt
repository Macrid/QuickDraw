package com.example.quickdraw.adapters

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.quickdraw.R
import com.example.quickdraw.fragments.FriendlistFragment
import com.example.quickdraw.models.User

class FriendlistAdapter() : RecyclerView.Adapter<FriendlistViewHolder>() {

    lateinit var mainFragment: FriendlistFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendlistViewHolder {
        val vh = FriendlistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false))
        return vh
    }

    override fun getItemCount(): Int {
        mainFragment.friendlistVM.getAllFriends().size.let {friends ->
            mainFragment.friendlistVM.getAllFriendRequests().size.let {friendRequests ->
                return friends + friendRequests
            }
        }
        return 0
    }

    override fun onBindViewHolder(holder: FriendlistViewHolder, position: Int) {
        var currentFriend: User
        if (position < mainFragment.friendlistVM.getAllFriends().size)
        {
            currentFriend = mainFragment.friendlistVM.getAllFriends()[position]
        }
        else
        {
            currentFriend = mainFragment.friendlistVM.getAllFriendRequests()[position - mainFragment.friendlistVM.getAllFriends().size]
        }

        holder.displayNameTextView.text = currentFriend.displayName
        holder.friendIDTextView.text = currentFriend.ID

        holder.friendAcceptButton.setOnClickListener {

        }

        holder.friendRejectButton.setOnClickListener {

        }
    }

}

class FriendlistViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val displayNameTextView = view.findViewById<TextView>(R.id.friendlistNameTextView)
    val friendIDTextView = view.findViewById<TextView>(R.id.friendlistIDTextView)
    val friendAcceptButton = view.findViewById<Button>(R.id.acceptFriendButton)
    val friendRejectButton = view.findViewById<Button>(R.id.rejectFriendButton)

}