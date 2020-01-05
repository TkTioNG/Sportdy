package com.example.sportdy.Game


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportdy.Database.SportGame
import com.example.sportdy.Database.SportGameViewModel

import com.example.sportdy.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_my_game.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class MyGameFragment : Fragment() {

    private lateinit var sportGameViewModel: SportGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_game, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewGame)
        val adapter = GameAdapter(activity!!.applicationContext)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        sportGameViewModel = ViewModelProvider(this).get(SportGameViewModel::class.java)

        sportGameViewModel.allSportGames.observe(viewLifecycleOwner,
            Observer { sportGameList: List<SportGame> ->
                sportGameList.let {
                    if (it.isNotEmpty()) {
                        adapter.setSportGame(it)
                        Toast.makeText(activity!!.applicationContext, "Number: " + it.size, Toast.LENGTH_SHORT).show()
                    }
                }
            })

        val fabAddGame = view.findViewById<FloatingActionButton>(R.id.fabAddGame)
        fabAddGame.setOnClickListener {
            val intent = Intent(activity!!.applicationContext, AddGameActivity::class.java)
            startActivity(intent)
            //startActivityForResult(intent, ADD_GAME_REQUEST_CODE)
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

    companion object {
        const val ADD_GAME_REQUEST_CODE = 1
    }
}
