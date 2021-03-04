package com.example.quickdraw.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.quickdraw.models.Game
import com.example.quickdraw.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainMenuViewModel : ViewModel() {
    private var activeGames = mutableListOf<Game>()
    private var pendingGames = mutableListOf<Game>()
    private var activeGameIDs = mutableListOf<String>()
    private var pendingGameIDs = mutableListOf<String>()

    fun getActiveGames() : MutableList<Game>
    {
        return activeGames
    }
    fun getPendingGames() : MutableList<Game>
    {
        return  pendingGames
    }

    fun getGameIDs(function: () -> Unit)
    {
        activeGameIDs.clear()
        pendingGames.clear()

        val databaseRef = Firebase.database.reference.child("Users").child(Firebase.auth.uid!!)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (gameID in dataSnapshot.child("Active Games").children)
                {
                    activeGameIDs.add(gameID.value.toString())
                    Log.w("Debug", gameID.value.toString())
                }
                for (gameID in dataSnapshot.child("Pending game invites").children)
                {
                    pendingGameIDs.add(gameID.value.toString())
                }
                loadGames(function)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Debug", databaseError.toException())
            }
        }
        databaseRef.addListenerForSingleValueEvent(postListener)
    }

    fun loadGames(function: () -> Unit)
    {
        activeGames.clear()
        pendingGames.clear()

        val databaseRef = Firebase.database.reference
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                activeGameIDs.forEach()
                {
                    var tempGame = Game()

                    var tempPlayers = mutableListOf<User>()
                    var tempScore = mutableListOf<Map<String,Int>>()
                    for (userID in dataSnapshot.child("Games").child("Active Games").child(it).child("Players").children)
                    {
                        tempPlayers.add(dataSnapshot.child("Users").child(userID.key!!) as User)
                        var scoreMap = mapOf(userID.key.toString() to userID.child("Points").value as Int)
                        tempScore.add(scoreMap)
                    }
                    tempGame.score = tempScore
                    tempGame.players = tempPlayers
                    tempGame.roundCounter = dataSnapshot.child("Games").child("Active games").child(it).child("Round").value as Long
                    activeGames.add(tempGame)
                    Log.w("Debug", activeGames.size.toString())
                    function()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Debug", databaseError.toException())
            }
        }
        databaseRef.addListenerForSingleValueEvent(postListener)
    }
}
