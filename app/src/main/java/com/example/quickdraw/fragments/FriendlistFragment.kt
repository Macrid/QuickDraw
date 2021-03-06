package com.example.quickdraw.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickdraw.adapters.FriendlistAdapter
import com.example.quickdraw.R
import com.example.quickdraw.adapters.FriendlistSearchAdapter
import com.example.quickdraw.viewmodels.FriendlistViewModel

class FriendlistFragment : Fragment() {

    lateinit var friendlistVM: FriendlistViewModel
    var friendlistSearchAdapter = FriendlistSearchAdapter()
    var friendlistAdapter = FriendlistAdapter()

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
        friendlistVM = ViewModelProvider(this).get(FriendlistViewModel::class.java)
        friendlistAdapter.mainFragment = this
        var friendlistView = requireView().findViewById<RecyclerView>(R.id.friendListRV)
        friendlistView.layoutManager = LinearLayoutManager(this.context)
        friendlistView.adapter = friendlistAdapter

       // friendlistVM.loadData { (friendlistView.adapter as FriendlistAdapter).notifyDataSetChanged() }
        friendlistVM.loadData { friendlistAdapter.notifyDataSetChanged() }

        friendlistSearchAdapter.mainFragment = this

        val friendSearch = requireView().findViewById<SearchView>(R.id.friendSearch)
        val friendSearchListView = requireView().findViewById<RecyclerView>(R.id.friendSearchListView)

        friendSearchListView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        friendSearchListView.adapter = friendlistSearchAdapter

        friendSearch.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                friendlistVM.filterUsers(query!!)
                friendlistSearchAdapter.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               // friendlistVM.filterUsers(newText!!)
                return false
            }
        })
    }
}