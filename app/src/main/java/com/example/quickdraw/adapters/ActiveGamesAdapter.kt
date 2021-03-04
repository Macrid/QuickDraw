package com.example.quickdraw.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.Navigation
import com.example.quickdraw.R
import com.example.quickdraw.fragments.MainMenuFragment
import com.example.quickdraw.models.Game

class ActiveGamesAdapter() : RecyclerView.Adapter<ActiveGamesViewHolder>() {

    lateinit var mainFragment: MainMenuFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveGamesViewHolder {
        val vh = ActiveGamesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.active_games_item, parent, false))
        return vh
    }

    override fun getItemCount(): Int {
        mainFragment.mainMenuVM.getActiveGames().size.let {
            Log.w("Debug", it.toString())
            return it
        }
        return 0
    }

    override fun onBindViewHolder(holder: ActiveGamesViewHolder, position: Int) {
        var currentGame : Game = mainFragment.mainMenuVM.getActiveGames()[position]

        holder.gameRoundTV.text = currentGame.roundCounter.toString()
        currentGame.score.let { scoreList ->
            scoreList!!.forEach {
                holder.player1ScoreTV.text = it.getValue("Player1").toString()
                holder.player2ScoreTV.text = it.getValue("Player2").toString()
                holder.player3ScoreTV.text = it.getValue("Player3").toString()
            }
        }

        holder.itemView.setOnClickListener {
            Navigation.findNavController(mainFragment.requireView()).navigate(R.id.action_mainMenuFragment_to_gameOverviewFragment)
        }

    }

}

class ActiveGamesViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    var player1ScoreTV = view.findViewById<TextView>(R.id.activeGameItemPlayer1Score)
    var player2ScoreTV = view.findViewById<TextView>(R.id.activeGameItemPlayer2Score)
    var player3ScoreTV = view.findViewById<TextView>(R.id.activeGameItemPlayer3Score)
    var gameRoundTV = view.findViewById<TextView>(R.id.activeGameItemRoundCounter)
}