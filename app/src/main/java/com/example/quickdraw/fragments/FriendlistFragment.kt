package com.example.quickdraw.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickdraw.FriendlistAdapter
import com.example.quickdraw.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

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
        var friendlistAdapter = FriendlistAdapter()
        friendlistAdapter.mainFragment = this
        var friendlistView = requireView().findViewById<RecyclerView>(R.id.friendListRV)
        friendlistView.layoutManager = LinearLayoutManager(this.context)
        friendlistView.adapter = friendlistAdapter


        val friendSearch = requireView().findViewById<SearchView>(R.id.friendSearch)
        val friendSearchListView = requireView().findViewById<ListView>(R.id.friendSearchListView)

        var userDisplayNames = mutableListOf<String>()

        userDisplayNames.add("Test")

        val friendSearchAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.friend_search_item, userDisplayNames)

        friendSearchListView.adapter = friendSearchAdapter

        val databaseRef = Firebase.database.reference.child("Users")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (user in dataSnapshot.children)
                {
                    Log.w("Debug", user.child("Display Name").value as String)
                    userDisplayNames.add(user.child("Display Name").value as String)
                }
                friendSearchAdapter.notifyDataSetChanged()

                Log.w("Debug", userDisplayNames.count().toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Debug", databaseError.toException())
            }
        }
        databaseRef.addListenerForSingleValueEvent(postListener)


        friendSearch.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                friendSearch.clearFocus()
                if(userDisplayNames.contains(query))
                {
                    friendSearchAdapter.filter.filter(query)
                }
                else{
                    Toast.makeText(requireContext().applicationContext, "Found nobody with that name :(", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                friendSearchAdapter.filter.filter(newText)
                return false
            }
        })
    }
}