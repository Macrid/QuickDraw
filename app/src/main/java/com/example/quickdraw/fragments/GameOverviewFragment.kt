package com.example.quickdraw.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.quickdraw.R


class GameOverviewFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_game_overview, container, false)
    }

    override fun onStart() {
        super.onStart()
        requireView().findViewById<Button>(R.id.goDraw).setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_gameOverviewFragment_to_drawingFragment)
        }

    }
}