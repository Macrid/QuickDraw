package com.example.quickdraw.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickdraw.adapters.ActiveGamesAdapter
import com.example.quickdraw.R


class MainMenuFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onStart() {
        super.onStart()
        requireView().findViewById<Button>(R.id.friendlistButton).setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_mainMenuFragment_to_friendlistFragment)
        }
        var activeGamesAdapter = ActiveGamesAdapter()
        activeGamesAdapter.mainFragment = this
        var recView = requireView().findViewById<RecyclerView>(R.id.activeGamesRV)

        recView.layoutManager = LinearLayoutManager(this.context)
        recView.adapter = activeGamesAdapter
    }
}