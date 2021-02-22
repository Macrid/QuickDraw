package com.example.quickdraw.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quickdraw.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FriendlistViewModel : ViewModel() {
    //var userDisplayNames = mutableListOf<String>()
    private var allUsers = mutableListOf<User>()
    private var filteredUsers = mutableListOf<User>()

    fun getFilteredUsers() : MutableList<User>
    {
        return  filteredUsers
    }

    fun getUsers() : MutableList<User>
    {
        return allUsers
    }

    fun filterUsers(searchValue : String)
    {
        filteredUsers.clear()
        allUsers.forEach{
            if (it.displayName!!.contains(searchValue))
            {
                filteredUsers.add(it)
            }
        }
        Log.w("debug", filteredUsers.toString())
    }

    fun loadUsers()
    {
        val databaseRef = Firebase.database.reference.child("Users")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (user in dataSnapshot.children)
                {
                    /*
                    Log.w("Debug", user.child("displayName").value as String)
                    userDisplayNames.add(user.child("displayName").value as String)

                     */
                    var tempUser = user.getValue<User>()!!
                    allUsers.add(tempUser)
                }
                //Log.w("Debug", userDisplayNames.count().toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Debug", databaseError.toException())
            }
        }
        databaseRef.addListenerForSingleValueEvent(postListener)

    }

    fun addFriend(addedUserID : String)
    {
        Firebase.database.reference.child("Users").child(addedUserID).child("Pending friend requests").child("SenderUserID").setValue(Firebase.auth.uid)
    }
}