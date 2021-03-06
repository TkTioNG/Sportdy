package com.example.sportdy.Database

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sportdy.App
import com.example.sportdy.Game.GameSingleton
import com.example.sportdy.R
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class SportGameViewModel(application: Application): AndroidViewModel(application) {
    private val repository: SportGameRepository
    val allSportGames: LiveData<List<SportGame>>
    val userSportGames: LiveData<List<SportGame>>
    val othersSportGames: LiveData<List<SportGame>>
    val historySportGames: LiveData<List<SportGame>>

    init {
        val sportGameDao = SportGameDatabase.getInstance(application).sportGameDao()
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("users", Context.MODE_PRIVATE)
        val hoster_name = sharedPreferences.getString("name_key","name")!!
        repository = SportGameRepository(sportGameDao, hoster_name)
        allSportGames = repository.allSportGames
        userSportGames = repository.userSportGames
        othersSportGames = repository.othersSportGames
        historySportGames = repository.historySportGames
    }

    fun insertSportGame(sportGame: SportGame) = viewModelScope.launch {
        repository.insertSportGame(sportGame)
    }

    fun syncSportGame(sportGames: List<SportGame>) = viewModelScope.launch {
        repository.syncSportGame(sportGames)
    }

    fun updateSportGame(gameID: Int, gameName:String, game_type:String, game_date:Long, game_time:Int, location:String, street1:String, street2:String, area:String, postcode:Int, state:String, maxppl:Int, description:String) = viewModelScope.launch {
        repository.updateSportGame(gameID, gameName, game_type, game_date, game_time, location, street1, street2, area, postcode, state, maxppl, description)
    }

    fun deleteSportGame(gameID: Int) = viewModelScope.launch {
        repository.deleteSportGame(gameID)
    }

    fun joinGame(gameID: Int) = viewModelScope.launch {
        repository.joinGame(gameID)
    }

    fun unjoinGame(gameID: Int) = viewModelScope.launch {
        repository.unjoinGame(gameID)
    }

}