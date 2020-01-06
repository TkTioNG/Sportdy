package com.example.sportdy.Game


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportdy.Database.SportGame
import com.example.sportdy.Database.SportGameViewModel

import com.example.sportdy.R

/**
 * A simple [Fragment] subclass.
 */
class FindGameFragment : Fragment(), GameAdapter.OnGameClickListener {

    private lateinit var sportGameViewModel: SportGameViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        navController = activity!!.findNavController(R.id.mainHostFragment)

        val view = inflater.inflate(R.layout.fragment_find_game, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewGame)
        val adapter = GameAdapter(activity!!.applicationContext, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        sportGameViewModel = ViewModelProvider(this).get(SportGameViewModel::class.java)

        sportGameViewModel.othersSportGames.observe(viewLifecycleOwner,
            Observer { sportGameList: List<SportGame> ->
                sportGameList.let {
                    if (it.isNotEmpty()) {
                        adapter.setSportGame(it)
                        Toast.makeText(activity!!.applicationContext, "Number: " + it.size, Toast.LENGTH_SHORT).show()
                    }
                }
            })

        return view
    }


    override fun onGameClick(position: Int) {
        Log.d("FindGame","Clicked ${position}")
        val sportGame = sportGameViewModel.othersSportGames.value!!.get(position)
        var bundle =
            bundleOf(
                "gameName" to sportGame.gameName,
                "gameType" to sportGame.gameType,
                "gameDate" to sportGame.gameDate,
                "gameTime" to sportGame.gameTime,
                "location" to sportGame.location,
                "street1" to sportGame.street1,
                "street2" to sportGame.street2,
                "area" to sportGame.area,
                "postcode" to sportGame.postcode,
                "state" to sportGame.state,
                "maxppl" to sportGame.maxppl,
                "nowppl" to sportGame.nowppl,
                "description" to sportGame.description,
                "hosterName" to sportGame.hosterName
            )
        navController.navigate(R.id.action_gameFragment_to_testingFragment, bundle)
    }
}
