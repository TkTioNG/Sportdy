package com.example.sportdy

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sportdy.Login.LoginActivity
import com.example.sportdy.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.lifecycle.ViewModelProvider
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sportdy.Database.SportGame
import com.example.sportdy.Database.SportGameViewModel
import com.example.sportdy.Game.GameFragment
import com.example.sportdy.Game.GameSingleton
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_login.*

import kotlinx.android.synthetic.main.activity_register.*


class MainActivity : AppCompatActivity(), DrawerLocker {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    private val sharedPrefFile = "users"
    private lateinit var sportGameViewModel: SportGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var navView = findViewById<NavigationView>(R.id.navView)
//        navView.setNavigationItemSelectedListener(this)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        )

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // Show hamburger
        toggle.setDrawerIndicatorEnabled(true)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar!!.setTitle(R.string.game)

        navController = this.findNavController(R.id.mainHostFragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.gameFragment,
                R.id.chatFragment,
                R.id.friendFragment,
                R.id.communityFragment,
                R.id.settingsFragment,
                R.id.aboutUsFragment,
                R.id.helpFragment,
                R.id.loginActivity
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        sportGameViewModel = ViewModelProvider(this).get(SportGameViewModel::class.java)
        checkSync()
//
//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//        NavigationUI.setupWithNavController(binding.navView,navController)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val sharedNameValue = sharedPreferences.getString("name_key","name")


        val navigationView : NavigationView =findViewById(R.id.navView)
        val headerView : View = navigationView.getHeaderView(0)
        val navHeaderName = headerView.findViewById<TextView>(R.id.navHeaderName)
        navHeaderName.text=sharedNameValue
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
        GameSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun setDrawerEnabled(enabled: Boolean) {
        val lockMode = if (enabled)
            DrawerLayout.LOCK_MODE_UNLOCKED
        else
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED

        //toggle.isDrawerIndicatorEnabled = enabled
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(!enabled)
        supportActionBar!!.setDisplayHomeAsUpEnabled(!enabled)
        drawerLayout.setDrawerLockMode(lockMode)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.loginActivity){
            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }else{
            return super.onOptionsItemSelected(item)
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        val fragment = when(item.itemId) {
//            R.id.gameFragment ->
//                GameFragment()
//            R.id.chatFragment ->
//                ChatFragment()
//            R.id.friendFragment ->
//                FriendFragment()
//            R.id.communityFragment ->
//                CommunityFragment()
//            R.id.settingsFragment ->
//                SettingsFragment()
//            R.id.aboutUsFragment ->
//                AboutUsFragment()
//            R.id.helpFragment ->
//                HelpFragment()
//            else ->
//                GameFragment()
//        }
//
//        supportFragmentManager.beginTransaction().replace(R.id.mainHostFragment, fragment).commit()
//        drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.mainHostFragment)
//        return NavigationUI.navigateUp(navController, drawerLayout)
//    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.navdrawer_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val navController = this.findNavController(R.id.mainHostFragment)
//
//        //You must regain the power of swipe for the drawer.
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        // Remove back button
//        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(false);
//        // Show hamburger
//        toggle.setDrawerIndicatorEnabled(true);
//        // Remove the/any drawer toggle listener
//        toggle.setToolbarNavigationClickListener(null);
//
//        navController.navigate(item.itemId)
//
//        return true
//    }
}

public interface DrawerLocker {
    fun setDrawerEnabled(enabled: Boolean)
}
