package com.example.quickdraw.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.quickdraw.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onStart()
        auth = Firebase.auth
        var databaseRef = Firebase.database.reference

        var loginButton = requireView().findViewById<Button>(R.id.loginButton)
        var registerButton = requireView().findViewById<Button>(R.id.registerButton)
        var emailField = requireView().findViewById<EditText>(R.id.editTextEmailAddress)
        var passwordField = requireView().findViewById<EditText>(R.id.editTextPassword)

        loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(emailField.text.toString(), passwordField.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_mainMenuFragment)
                } else {
                    Log.d("DEBUG", "fel inlogg")
                }
            }
        }

        registerButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(emailField.text.toString(), passwordField.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_mainMenuFragment)
                    databaseRef.child("Users").child(auth.uid!!).child("Display Name").setValue("Fint namn")
                    databaseRef.child("Users").child(auth.uid!!).child("ID").setValue(auth.uid!!)
                } else {
                    Log.d("DEBUG", "fel reg")
                }
            }
        }
    }
}