package com.example.sportdy.Game


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.example.sportdy.Game.GameFragment.Companion.ADD_GAME_REQUEST_CODE
import com.example.sportdy.Game.GameFragment.Companion.FROM_MY_GAME_FRAGMENT

import com.example.sportdy.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_my_game.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class MyGameFragment : Fragment(), GameAdapter.OnGameClickListener {

    private lateinit var sportGameViewModel: SportGameViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_my_game, container, false)
        navController = activity!!.findNavController(R.id.mainHostFragment)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewGame)
        val adapter = GameAdapter(activity!!.applicationContext, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        sportGameViewModel = ViewModelProvider(this).get(SportGameViewModel::class.java)

        sportGameViewModel.userSportGames.observe(viewLifecycleOwner,
            Observer { sportGameList: List<SportGame> ->
                sportGameList.let {
                    if (it.isNotEmpty()) {
                        adapter.setSportGame(it)
                        Toast.makeText(activity!!.applicationContext, "Number: " + it.size, Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(activity!!.applicationContext, "Number: " + it.size, Toast.LENGTH_SHORT).show()
                    }
                }
            })

        val fabAddGame = view.findViewById<FloatingActionButton>(R.id.fabAddGame)
        fabAddGame.setOnClickListener {
            val intent = Intent(activity!!.applicationContext, AddGameActivity::class.java)
            //startActivity(intent)
            startActivityForResult(intent, ADD_GAME_REQUEST_CODE)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_GAME_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                    val _game_name = data?.getStringExtra(AddGameActivity.ADD_GAME_NAME)
                    val _game_type = data?.getStringExtra(AddGameActivity.ADD_GAME_TYPE)
                    val _game_date = data?.getLongExtra(
                        AddGameActivity.ADD_GAME_DATE,
                        System.currentTimeMillis() + 604800000
                    )
                    val _game_time = data?.getIntExtra(AddGameActivity.ADD_GAME_TIME, 1080)
                    val _game_location = data?.getStringExtra(AddGameActivity.ADD_GAME_LOCATION)
                    val _game_street1 = data?.getStringExtra(AddGameActivity.ADD_GAME_STREET1)
                    val _game_street2 = data?.getStringExtra(AddGameActivity.ADD_GAME_STREET2)
                    val _game_area = data?.getStringExtra(AddGameActivity.ADD_GAME_AREA)
                    val _game_postcode = data?.getIntExtra(AddGameActivity.ADD_GAME_POSTCODE, 53300)
                    val _game_state = data?.getStringExtra(AddGameActivity.ADD_GAME_STATE)
                    val _game_maxppl = data?.getIntExtra(AddGameActivity.ADD_GAME_MAXPPL, 20)
                    val _game_nowppl = data?.getIntExtra(AddGameActivity.ADD_GAME_NOWPPL, 1)
                    val _game_desc = data?.getStringExtra(AddGameActivity.ADD_GAME_DESC)
                    val _game_hostername = data?.getStringExtra(AddGameActivity.ADD_GAME_HOSTERNAME)

                    val sportGame = SportGame(gameID = 0, gameName = _game_name!!, gameType = _game_type!!, gameDate = _game_date!!, gameTime = _game_time!!, location = _game_location!!,
                        street1 = _game_street1!!, street2 = _game_street2!!, area = _game_area!!, postcode = _game_postcode!!, state = _game_state!!,
                        maxppl = _game_maxppl!!, nowppl = _game_nowppl!!, description = _game_desc!!, hosterName = _game_hostername!!)

                    sportGameViewModel.insertSportGame(sportGame)

                    Toast.makeText(activity!!.applicationContext, "You have add new sport game.", Toast.LENGTH_SHORT).show()

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onGameClick(position: Int) {
        Log.d("FindGame","Clicked ${position}")
        val sportGame = sportGameViewModel.userSportGames.value!!.get(position)
        var bundle =
            bundleOf(
                "type" to FROM_MY_GAME_FRAGMENT,
                "gameID" to sportGame.gameID,
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
