package com.example.quickdraw.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickdraw.adapters.ActiveGamesAdapter
import com.example.quickdraw.R
import com.example.quickdraw.models.Game
import com.example.quickdraw.models.User
import com.example.quickdraw.viewmodels.MainMenuViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainMenuFragment : Fragment() {
    lateinit var mainMenuVM : MainMenuViewModel
    var activeGamesAdapter = ActiveGamesAdapter()

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
        mainMenuVM = ViewModelProvider(this).get(MainMenuViewModel::class.java)
        activeGamesAdapter.mainFragment = this

        var recView = requireView().findViewById<RecyclerView>(R.id.activeGamesRV)
        recView.layoutManager = LinearLayoutManager(this.context)
        recView.adapter = activeGamesAdapter

        mainMenuVM.getGameIDs(){
            activeGamesAdapter.notifyDataSetChanged()
        }

        requireView().findViewById<Button>(R.id.friendlistButton).setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_mainMenuFragment_to_friendlistFragment)
        }
        requireView().findViewById<Button>(R.id.settingsButton).setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_mainMenuFragment_to_settingsFragment)
        }


        val myFriends = mainMenuVM.getFriendsDisplayNames()


        val firstSpinner = requireView().findViewById<Spinner>(R.id.firstInviteSpinner)
        val adapter = ArrayAdapter<String>(this.requireActivity(),R.layout.choose_friend_spinner_item,R.id.spinnerTextView, myFriends)
        adapter.setDropDownViewResource(R.layout.choose_friend_spinner_item)
        firstSpinner.adapter = adapter
        firstSpinner.setSelection(0)
        mainMenuVM.loadFriends { adapter.notifyDataSetChanged() }



        firstSpinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                adapter.notifyDataSetChanged()
            }

        }

        requireView().findViewById<Button>(R.id.newGameButton).setOnClickListener {
            requireView().findViewById<ConstraintLayout>(R.id.mainMenuNewGameOverlayCL).isGone = false
            Log.w("debug", myFriends.toString())
        }
    }
}