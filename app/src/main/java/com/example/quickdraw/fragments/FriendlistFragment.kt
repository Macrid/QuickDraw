package com.example.quickdraw.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickdraw.ActiveGamesAdapter
import com.example.quickdraw.FriendlistAdapter
import com.example.quickdraw.R

class FriendlistFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friendlist, container, false)
    }

    override fun onStart() {
        super.onStart()
        var frienlistAdapter = FriendlistAdapter()
        frienlistAdapter.mainFragment = this
        var friendlistView = requireView().findViewById<RecyclerView>(R.id.friendListRV)

        friendlistView.layoutManager = LinearLayoutManager(this.context)
        friendlistView.adapter = frienlistAdapter
    }
}