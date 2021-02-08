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
import com.example.quickdraw.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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

        var loginButton = requireView().findViewById<Button>(R.id.loginButton)
        var emailField = requireView().findViewById<EditText>(R.id.editTextEmailAddress)
        var passwordField = requireView().findViewById<EditText>(R.id.editTextPassword)

        loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(emailField.text.toString(), passwordField.text.toString()).addOnCompleteListener {  task ->
                if (task.isSuccessful) {
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_mainMenuFragment)
                } else {
                    Log.d("DEBUG", "fel inlogg")
                }
            }
        }
    }
}