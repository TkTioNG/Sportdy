package com.example.sportdy.Database

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData

class SportGameRepository(private val sportGameDao: SportGameDao, private val hoster_name: String) {

    val allSportGames: LiveData<List<SportGame>> = sportGameDao.getAll()
    val userSportGames: LiveData<List<SportGame>> = sportGameDao.getFrom(hoster_name,System.currentTimeMillis())
    val othersSportGames: LiveData<List<SportGame>> = sportGameDao.getNotFrom(hoster_name,System.currentTimeMillis())
    val historySportGames: LiveData<List<SportGame>> = sportGameDao.gethistory(System.currentTimeMillis())

    suspend fun insertSportGame(sportGame: SportGame) {
        sportGameDao.insert(sportGame)
    }

    suspend fun syncSportGame(sportGames: List<SportGame>) {
        sportGameDao.insertAll(sportGames)
    }

    suspend fun deleteSportGame(gameID: Int) {
        sportGameDao.deleteOne(gameID)
    }

    suspend fun updateSportGame(gameID: Int, gameName: String, game_type:String, game_date:Long, game_time:Int, location:String, street1:String, street2:String, area:String, postcode:Int, state:String, maxppl:Int, description:String) {
        sportGameDao.updateOne(gameID, gameName, game_type, game_date, game_time, location, street1, street2, area, postcode, state, maxppl, description)
    }

    suspend fun joinGame(gameID: Int) {
        sportGameDao.joinGame(gameID)
    }

    suspend fun unjoinGame(gameID: Int) {
        sportGameDao.unjoinGame(gameID)
    }
}