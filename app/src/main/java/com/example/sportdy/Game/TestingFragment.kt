package com.example.sportdy.Game


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.example.sportdy.R
import com.example.sportdy.DrawerLocker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sportdy.App
import com.example.sportdy.Database.SportGame
import com.example.sportdy.Database.SportGameViewModel
import com.example.sportdy.Game.GameFragment.Companion.EDIT_GAME_REQUEST_CODE
import com.example.sportdy.Game.GameFragment.Companion.FROM_FIND_GAME_FRAGMENT
import com.example.sportdy.Game.GameFragment.Companion.FROM_HISTORY_FRAGMENT
import com.example.sportdy.Game.GameFragment.Companion.FROM_MY_GAME_FRAGMENT
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import kotlin.random.Random


/**
 * A simple [Fragment] subclass.
 */
class TestingFragment() : Fragment() {

    private lateinit var tvGDGameID: TextView
    private lateinit var tvGDGameName: TextView
    private lateinit var tvGDHosterName: TextView
    private lateinit var tvGDGameType: TextView
    private lateinit var tvGDGameDate: TextView
    private lateinit var tvGDGameTime: TextView
    private lateinit var tvGDLocation: TextView
    private lateinit var tvGDStreet1: TextView
    private lateinit var tvGDStreet2: TextView
    private lateinit var tvGDArea: TextView
    private lateinit var tvGDPostcode: TextView
    private lateinit var tvGDState: TextView
    private lateinit var tvGDMaxPpl: TextView
    private lateinit var tvGDNowPpl: TextView
    private lateinit var tvGDDesc: TextView
    private lateinit var ivGDGameType: ImageView
    private lateinit var ivGDHoster: ImageView

    private var gameID: Int = 0

    private lateinit var sportGameViewModel: SportGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //(activity as DrawerLocker).setDrawerEnabled(false)
        val actionbar = (activity as AppCompatActivity).supportActionBar!!

        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDefaultDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_testing, container, false)

        sportGameViewModel = ViewModelProvider(this).get(SportGameViewModel::class.java)

        tvGDGameID = view.findViewById<TextView>(R.id.tvGDGameID)
        tvGDGameName = view.findViewById<TextView>(R.id.tvGDGameName)
        tvGDHosterName = view.findViewById<TextView>(R.id.tvGDHosterName)
        tvGDGameType = view.findViewById<TextView>(R.id.tvGDGameType)
        tvGDGameDate = view.findViewById<TextView>(R.id.tvGDGameDate)
        tvGDGameTime = view.findViewById<TextView>(R.id.tvGDGameTime)
        tvGDLocation = view.findViewById<TextView>(R.id.tvGDLocation)
        tvGDStreet1 = view.findViewById<TextView>(R.id.tvGDStreet1)
        tvGDStreet2 = view.findViewById<TextView>(R.id.tvGDStreet2)
        tvGDArea = view.findViewById<TextView>(R.id.tvGDArea)
        tvGDPostcode = view.findViewById<TextView>(R.id.tvGDPostcode)
        tvGDState = view.findViewById<TextView>(R.id.tvGDState)
        tvGDMaxPpl = view.findViewById<TextView>(R.id.tvGDMaxPpl)
        tvGDNowPpl = view.findViewById<TextView>(R.id.tvGDNowPpl)
        tvGDDesc = view.findViewById<TextView>(R.id.tvGDDesc)
        ivGDGameType = view.findViewById<ImageView>(R.id.ivGDGameType)
        ivGDHoster = view.findViewById<ImageView>(R.id.ivGDHoster)

        val type = arguments?.getInt("type", 0)
        tvGDGameID.text = arguments?.getInt("gameID").toString()
        tvGDGameName.text = arguments?.getString("gameName")
        tvGDHosterName.text = arguments?.getString("hosterName")
        tvGDGameType.text = arguments?.getString("gameType")
        tvGDGameDate.text = SimpleDateFormat("dd MMM yyyy").format(arguments?.getLong("gameDate", 0))
        var time = arguments?.getInt("gameTime", 0)
        var hour = time!! / 60
        var amPm : String = "AM"
        if (hour > 12) {
            amPm = "PM"
            hour -= 12
        }
        tvGDGameTime.text = String.format("%02d:%02d %s", hour, time % 60, amPm)
        tvGDLocation.text = arguments?.getString("location")
        tvGDStreet1.text = arguments?.getString("street1")
        tvGDStreet2.text = arguments?.getString("street2")
        tvGDArea.text = arguments?.getString("area")
        tvGDPostcode.text = arguments?.getInt("postcode",0).toString()
        tvGDState.text = arguments?.getString("state")
        tvGDMaxPpl.text = arguments?.getInt("maxppl", 10).toString()
        tvGDNowPpl.text = arguments?.getInt("nowppl", 1).toString()
        tvGDDesc.text = arguments?.getString("description").toString()
        ivGDGameType.setImageResource(getGameTypeImage(tvGDGameType.text.toString()))
        ivGDHoster.setImageResource(getHosterImage(tvGDHosterName.text.toString()))

        val btnUpdateGame = view.findViewById<Button>(R.id.btnUpdateGame)
        val btnJoinGame = view.findViewById<Button>(R.id.btnJoinGame)
        val btnUnjoinGame = view.findViewById<Button>(R.id.btnUnjoinGame)
        val btnRemoveGame = view.findViewById<Button>(R.id.btnRemoveGame)

        when(type) {
            FROM_FIND_GAME_FRAGMENT -> {
                btnUpdateGame.visibility = View.GONE
                btnJoinGame.visibility = View.VISIBLE
                btnUnjoinGame.visibility = View.GONE
                btnRemoveGame.visibility = View.GONE
            }
            FROM_MY_GAME_FRAGMENT -> {
                btnUpdateGame.visibility = View.VISIBLE
                btnJoinGame.visibility = View.GONE
                btnUnjoinGame.visibility = View.GONE
                btnRemoveGame.visibility = View.VISIBLE
            }
            FROM_HISTORY_FRAGMENT -> {
                btnUpdateGame.visibility = View.GONE
                btnJoinGame.visibility = View.GONE
                btnUnjoinGame.visibility = View.GONE
                btnRemoveGame.visibility = View.GONE
            }
            else -> {
                btnUpdateGame.visibility = View.GONE
                btnJoinGame.visibility = View.GONE
                btnUnjoinGame.visibility = View.GONE
                btnRemoveGame.visibility = View.GONE
            }
        }

        if (tvGDHosterName.text.toString() != "TkTioNG" && btnJoinGame.visibility == View.GONE) {
            btnUpdateGame.visibility = View.GONE
            btnUnjoinGame.visibility = View.VISIBLE
            btnRemoveGame.visibility = View.GONE
        }

        btnUpdateGame.setOnClickListener(onUpdateGame())
        btnRemoveGame.setOnClickListener(onRemoveGame())
        btnJoinGame.setOnClickListener(onJoinGame())
        btnUnjoinGame.setOnClickListener(onUnjoinGame())
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.share_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuShare -> {
                val shareIntent = Intent()
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my game.");
                shareIntent.setType("text/plain");
                startActivity(shareIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onUpdateGame(): View.OnClickListener {
        return View.OnClickListener {
            gameID = tvGDGameID.text.toString().toInt()

            val intent = Intent(activity!!.applicationContext, AddGameActivity::class.java)

            val game_name = tvGDGameName.text.toString()
            val game_type = tvGDGameType.toString()
            val game_date = tvGDGameDate.text.toString()
            val game_time = tvGDGameTime.text.toString()
            val game_location = tvGDLocation.text.toString()
            val game_street1 = tvGDStreet1.text.toString()
            val game_street2 = tvGDStreet2.text.toString()
            val game_area = tvGDArea.text.toString()
            val game_postcode = tvGDPostcode.text.toString()
            val game_state = tvGDState.text.toString()
            val game_max_ppl = tvGDMaxPpl.text.toString()
            val game_now_ppl = "1"
            val game_desc = tvGDDesc.text.toString()
            val game_hostername = tvGDHosterName.text.toString()

            intent.putExtra(EDIT_GAME_CODE, EDIT_GAME_REQUEST_CODE)
            intent.putExtra(ADD_GAME_NAME, game_name)
            intent.putExtra(ADD_GAME_TYPE, game_type)
            intent.putExtra(ADD_GAME_DATE, game_date)
            intent.putExtra(ADD_GAME_TIME, game_time)
            intent.putExtra(ADD_GAME_LOCATION, game_location)
            intent.putExtra(ADD_GAME_STREET1, game_street1)
            intent.putExtra(ADD_GAME_STREET2, game_street2)
            intent.putExtra(ADD_GAME_AREA, game_area)
            intent.putExtra(ADD_GAME_POSTCODE, game_postcode)
            intent.putExtra(ADD_GAME_STATE, game_state)
            intent.putExtra(ADD_GAME_MAXPPL, game_max_ppl)
            intent.putExtra(ADD_GAME_NOWPPL, game_now_ppl)
            intent.putExtra(ADD_GAME_DESC, game_desc)
            intent.putExtra(ADD_GAME_HOSTERNAME, game_hostername)

            startActivityForResult(intent, EDIT_GAME_REQUEST_CODE)
        }
    }

    private fun onRemoveGame(): View.OnClickListener {
        return View.OnClickListener {
            gameID = tvGDGameID.text.toString().toInt()

            onWebSportGameDelete(gameID)

            sportGameViewModel.deleteSportGame(gameID)

            checkSync()

            Snackbar.make(this.view!!, "You have deleted one record", Snackbar.LENGTH_LONG).setAction("Undo", View.OnClickListener {
                fun onClick(view: View) {
                    Toast.makeText(
                        activity!!.applicationContext,
                        "You have restore back the sport game",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }).show()

            activity!!.onBackPressed()
        }
    }

    fun checkSync() {
        val url = App.context!!.resources.getString(R.string.url_server) + App.context!!.resources.getString(R.string.url_sport_game_read)
        Log.d("Main", url)
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
                        sportGameViewModel.syncSportGame(sportGames)
                        Log.d("Main", "Response-ReadGood: %d".format(size))
                    }
                    else {
                        Log.d("Main", "Response-Read: wow")
                    }
                } catch (e: Exception) {
                    Log.d("Main", "Response-Read1: %s".format(e.message.toString()))
                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response-Reaad2: %s".format(error.message.toString()))
            }
        )

        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        // Access the RequestQueue through your singleton class.
        GameSingleton.getInstance(activity!!.applicationContext).addToRequestQueue(jsonObjectRequest)
    }

    private fun onJoinGame(): View.OnClickListener {
        return View.OnClickListener {
            gameID = tvGDGameID.text.toString().toInt()
            sportGameViewModel.joinGame(gameID)
            Snackbar.make(this.view!!, "You have just join a game", Snackbar.LENGTH_LONG).setAction("Undo", View.OnClickListener {
                fun onClick(view: View) {
                    sportGameViewModel.unjoinGame(gameID)
                    Toast.makeText(
                        activity!!.applicationContext,
                        "You have unjoin sport game",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }).show()
            activity!!.onBackPressed()
        }
    }

    private fun onUnjoinGame(): View.OnClickListener {
        return View.OnClickListener {
            gameID = tvGDGameID.text.toString().toInt()
            sportGameViewModel.unjoinGame(gameID)
            Snackbar.make(this.view!!, "You have just unjoin a game", Snackbar.LENGTH_LONG).setAction("Undo", View.OnClickListener {
                fun onClick(view: View) {
                    sportGameViewModel.joinGame(gameID)
                    Toast.makeText(
                        activity!!.applicationContext,
                        "You have join back the sport game",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }).show()
            activity!!.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EDIT_GAME_REQUEST_CODE) {
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
                val _game_desc = data?.getStringExtra(AddGameActivity.ADD_GAME_DESC)

                sportGameViewModel.updateSportGame(gameID, _game_name!!, _game_type!!, _game_date!!, _game_time!!, _game_location!!, _game_street1!!, _game_street2!!, _game_area!!, _game_postcode!!, _game_state!!, _game_maxppl!!, _game_desc!!)

                onWebSportGameUpdate(gameID, _game_name!!, _game_type!!, _game_date!!, _game_time!!, _game_location!!, _game_street1!!, _game_street2!!, _game_area!!, _game_postcode!!, _game_state!!, _game_maxppl!!, _game_desc!!)

                tvGDGameName.text = _game_name
                tvGDGameDate.text = SimpleDateFormat("dd MMM yyyy").format(_game_date)
                var time = _game_time
                var hour = time!! / 60
                var amPm : String = "AM"
                if (hour > 12) {
                    amPm = "PM"
                    hour -= 12
                }
                tvGDGameTime.text = String.format("%02d:%02d %s", hour, time % 60, amPm)
                tvGDLocation.text = _game_location
                tvGDStreet1.text = _game_street1
                tvGDStreet2.text = _game_street2
                tvGDArea.text = _game_area
                tvGDPostcode.text = _game_postcode.toString()
                tvGDState.text = _game_state
                tvGDMaxPpl.text = _game_maxppl.toString()
                tvGDDesc.text = _game_desc

                Toast.makeText(activity!!.applicationContext, "You have update sport game.", Toast.LENGTH_SHORT).show()

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun onWebSportGameUpdate(gameid: Int, gamename: String, gametype: String, gamedate: Long, gametime: Int, location: String, street1: String, street2: String, area: String, postcode: Int, state: String, maxppl: Int, desc: String) {
        val url = getString(R.string.url_server) + getString(R.string.url_sport_game_update) +
                "?gamename=" + gamename + "&gametype=" + gametype + "&gamedate=" + gamedate +
                "&gametime=" + gametime + "&location=" + location + "&street1=" + street1 +
                "&street2=" + street2 + "&area=" + area + "&postcode=" + postcode +
                "&state=" + state + "&maxppl=" + maxppl +
                "&description=" + desc + "&gameid=" + gameid

        Log.i("Main",url)
        //+ "?name=" + user.name + "&contact=" + user.contact

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        val success: String = jsonResponse.get("success").toString()

                        if(success.equals("1")){
                            Toast.makeText(activity!!.applicationContext, "Sport game is updated.", Toast.LENGTH_LONG).show()
                            //Add record to user list
                            //userList.add(user)
                        }else{
                            Toast.makeText(activity!!.applicationContext, "Sport game is not updated.", Toast.LENGTH_LONG).show()
                        }
                    }
                }catch (e: Exception){
                    Log.d("Main", "Response-1: %s".format(e.message.toString()))
                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response-2: %s".format(error.message.toString()))
            }
        )

        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        // Access the RequestQueue through your singleton class.
        GameSingleton.getInstance(activity!!.applicationContext).addToRequestQueue(jsonObjectRequest)
    }

    private fun onWebSportGameDelete(gameid: Int) {
        val url = getString(R.string.url_server) + getString(R.string.url_sport_game_delete) +
                "?gameid=" + gameid

        Log.i("Main",url)
        //+ "?name=" + user.name + "&contact=" + user.contact

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Process the JSON
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        val success: String = jsonResponse.get("success").toString()

                        if(success.equals("1")){
                            Toast.makeText(activity!!.applicationContext, "Sport game is deleted.", Toast.LENGTH_LONG).show()
                            //Add record to user list
                            //userList.add(user)
                        }else{
                            Toast.makeText(activity!!.applicationContext, "Sport game is not deleted.", Toast.LENGTH_LONG).show()
                        }
                    }
                }catch (e: Exception){
                    Log.d("Main", "Response-1: %s".format(e.message.toString()))
                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response-2: %s".format(error.message.toString()))
            }
        )

        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        // Access the RequestQueue through your singleton class.
        GameSingleton.getInstance(activity!!.applicationContext).addToRequestQueue(jsonObjectRequest)
    }

    private fun getGameTypeImage(gameType: String): Int {
        return when (gameType) {
            "Basketball" -> R.drawable.basketball_icon
            "Bowling" -> R.drawable.bowling_icon
            "Cycling" -> R.drawable.cycling_icon
            "Workout" -> R.drawable.gym_icon
            "Jogging" -> R.drawable.jogging_icon
            "Soccer" -> R.drawable.soccer_icon
            else -> R.drawable.basketball_icon
        }
    }

    private fun getHosterImage(hosterName: String): Int {
        if (hosterName == "TkTioNG"||hosterName == "Me")
            return R.drawable.tktiong_icon
        val random = Random.nextInt(6)
        return when (random) {
            0 -> R.drawable.long_hair_woman_icon
            1 -> R.drawable.mid_man_icon
            2 -> R.drawable.old_man_icon
            3 -> R.drawable.short_hair_woman_icon
            4 -> R.drawable.young_boy_icon
            else -> R.drawable.young_girl_icon
        }
    }

    companion object {
        const val EDIT_GAME_CODE = "com.example.sportdy.Game.EDIT_GAME_CODE"

        const val ADD_GAME_NAME = "com.example.sportdy.Game.GAME_NAME"
        const val ADD_GAME_TYPE = "com.example.sportdy.Game.GAME_TYPE"
        const val ADD_GAME_DATE = "com.example.sportdy.Game.GAME_DATE"
        const val ADD_GAME_TIME = "com.example.sportdy.Game.GAME_TIME"
        const val ADD_GAME_LOCATION = "com.example.sportdy.Game.GAME_LOCATION"
        const val ADD_GAME_STREET1 = "com.example.sportdy.Game.GAME_STREET1"
        const val ADD_GAME_STREET2 = "com.example.sportdy.Game.GAME_STREET2"
        const val ADD_GAME_AREA = "com.example.sportdy.Game.GAME_AREA"
        const val ADD_GAME_POSTCODE = "com.example.sportdy.Game.GAME_POSTCODE"
        const val ADD_GAME_STATE = "com.example.sportdy.Game.GAME_STATE"
        const val ADD_GAME_MAXPPL = "com.example.sportdy.Game.GAME_MAXPPL"
        const val ADD_GAME_NOWPPL = "com.example.sportdy.Game.GAME_NOWPPL"
        const val ADD_GAME_DESC = "com.example.sportdy.Game.GAME_DESC"
        const val ADD_GAME_HOSTERNAME = "com.example.sportdy.Game.GAME_HOSTERNAME"
    }
}
