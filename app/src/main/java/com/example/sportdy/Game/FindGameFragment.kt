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
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sportdy.Database.SportGame
import com.example.sportdy.Database.SportGameViewModel
import com.example.sportdy.Game.GameFragment.Companion.FROM_FIND_GAME_FRAGMENT

import com.example.sportdy.R
import org.json.JSONArray
import org.json.JSONObject

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
                        Toast.makeText(
                            activity!!.applicationContext,
                            "Number: " + it.size,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })

        return view
    }

    fun syncSportGame() {
        val url = getString(R.string.url_server) + getString(R.string.url_sport_game_read)

        var sportGames: ArrayList<SportGame> = ArrayList<SportGame>()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try {
                    if (response != null) {
                        val strResponse = response.toString()
                        val jsonResponse = JSONObject(strResponse)
                        val jsonArray: JSONArray = jsonResponse.getJSONArray("records")
                        val size: Int = jsonArray.length()
                        for (i in 0..size - 1) {
                            var jsonSG: JSONObject = jsonArray.getJSONObject(i)
                            var sportGame: SportGame = SportGame(
                                jsonSG.getInt("gameid"),
                                jsonSG.getString("gamename"),
                                jsonSG.getString("gametype"),
                                jsonSG.getLong("gamedate"),
                                jsonSG.getInt("gametime"),
                                jsonSG.getString("location"),
                                jsonSG.getString("street1"),
                                jsonSG.getString("street2"),
                                jsonSG.getString("area"),
                                jsonSG.getInt("postcode"),
                                jsonSG.getString("state"),
                                jsonSG.getInt("maxppl"),
                                jsonSG.getInt("nowppl"),
                                jsonSG.getString("description"),
                                jsonSG.getString("hostername")
                            )
                            //var user: User = User(jsonUser.getString("name"), jsonUser.getString("contact"))
                            sportGames.add(sportGame)
                            //userList.add(user)
                        }

                        Toast.makeText(
                            activity!!.applicationContext,
                            "Record found :" + size,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    Log.d("Main", "Response: %s".format(e.message.toString()))
                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response: %s".format(error.message.toString()))
            }
        )

        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )
    }

    override fun onGameClick(position: Int) {
        Log.d("FindGame", "Clicked ${position}")
        val sportGame = sportGameViewModel.othersSportGames.value!!.get(position)
        var bundle =
            bundleOf(
                "type" to FROM_FIND_GAME_FRAGMENT,
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
