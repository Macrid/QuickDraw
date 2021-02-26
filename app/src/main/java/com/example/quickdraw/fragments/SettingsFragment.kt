package com.example.quickdraw.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.quickdraw.R
import com.example.quickdraw.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onStart() {
        super.onStart()
        val databaseRef = Firebase.database.reference.child("Users").child(Firebase.auth.uid!!)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                requireView().findViewById<TextView>(R.id.yourDisplayNameEditText).text = dataSnapshot.child("displayName").value as String
                requireView().findViewById<TextView>(R.id.yourIDTextView).text = dataSnapshot.child("ID").value as String
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Debug", databaseError.toException())
            }
        }
        databaseRef.addListenerForSingleValueEvent(postListener)

        requireView().findViewById<Button>(R.id.changeDisplayNameButton).setOnClickListener {
            Firebase.database.reference.child("Users").child(Firebase.auth.uid!!).child("displayName").setValue(requireView().findViewById<EditText>(R.id.yourDisplayNameEditText).text.toString())
        }
        requireView().findViewById<Button>(R.id.logoutButton).setOnClickListener {
            Firebase.auth.signOut()
            Navigation.findNavController(requireView()).navigate(R.id.action_settingsFragment_to_loginFragment)
        }
    }
}