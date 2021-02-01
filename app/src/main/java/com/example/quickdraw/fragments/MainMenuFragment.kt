package com.example.quickdraw.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickdraw.ActiveGamesAdapter
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
        var activeGamesAdapter = ActiveGamesAdapter()
        activeGamesAdapter.mainFragment = this
        var recView = requireView().findViewById<RecyclerView>(R.id.activeGamesRV)

        recView.layoutManager = LinearLayoutManager(this.context)
        recView.adapter = activeGamesAdapter
    }
}