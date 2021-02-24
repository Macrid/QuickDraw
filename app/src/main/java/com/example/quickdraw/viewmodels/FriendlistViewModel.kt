package com.example.quickdraw.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.quickdraw.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FriendlistViewModel : ViewModel() {
    //var userDisplayNames = mutableListOf<String>()
    private var allUsers = mutableListOf<User>()
    private var filteredUsers = mutableListOf<User>()
    private var allFriends = mutableListOf<User>()
    private var allFriendRequests = mutableListOf<User>()

    fun getFilteredUsers() : MutableList<User>
    {
        return  filteredUsers
    }

    fun getAllFriends() : MutableList<User>
    {
        return allFriends
    }

    fun getAllFriendRequests() : MutableList<User>
    {
        return allFriendRequests
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

    fun loadData(notifyDataChangeFunction: () -> (Unit))
    {
        allUsers.clear()
        allFriends.clear()
        allFriendRequests.clear()
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
                loadFriends(notifyDataChangeFunction)
                loadFriendRequests(notifyDataChangeFunction)

                //Log.w("Debug", userDisplayNames.count().toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("Debug", databaseError.toException())
            }
        }
        databaseRef.addListenerForSingleValueEvent(postListener)

    }

    fun loadFriends(notifyDataChangeFunction: () -> (Unit))
    {
        val databaseRef = Firebase.database.reference.child("Users").child(Firebase.auth.uid!!).child("Friends")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (friend in dataSnapshot.children)
                {
                    var tempFriend = User()
                    tempFriend.ID = friend.value as String
                    allUsers.forEach(){
                        if (it.ID == tempFriend.ID)
                        {
                            tempFriend.displayName = it.displayName
                        }
                    }
                    allFriends.add(tempFriend)
                }
                notifyDataChangeFunction()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Debug", databaseError.toException())
            }
        }
        databaseRef.addListenerForSingleValueEvent(postListener)
    }

    fun loadFriendRequests(notifyDataChangeFunction: () -> (Unit))
    {
        val databaseRef = Firebase.database.reference.child("Users").child(Firebase.auth.uid!!).child("Pending friend requests")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (friend in dataSnapshot.children)
                {
                    var tempFriendRequest = User()
                    tempFriendRequest.ID = friend.value as String
                    allUsers.forEach(){
                        if (it.ID == tempFriendRequest.ID)
                        {
                            tempFriendRequest.displayName = it.displayName
                        }
                    }
                    allFriendRequests.add(tempFriendRequest)
                }
                notifyDataChangeFunction()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Debug", databaseError.toException())
            }
        }
        databaseRef.addListenerForSingleValueEvent(postListener)
    }

    fun addFriend(addedUserID : String)
    {
        Firebase.database.reference.child("Users").child(addedUserID).child("Pending friend requests").child(Firebase.auth.uid!!).setValue(Firebase.auth.uid)
    }

    fun acceptFriendRequest(senderUserID : String)
    {
        Firebase.database.reference.child("Users").child(Firebase.auth.uid!!).child("Friends").push().setValue(senderUserID)
        Firebase.database.reference.child("Users").child(senderUserID).child("Friends").push().setValue(Firebase.auth.uid!!)
        Firebase.database.reference.child("Users").child(Firebase.auth.uid!!).child("Pending friend requests").child(senderUserID).removeValue()
    }

    fun rejectFriendRequest(senderUserID : String)
    {
        Firebase.database.reference.child("Users").child(Firebase.auth.uid!!).child("Pending friend requests").child(senderUserID).removeValue()
    }
}