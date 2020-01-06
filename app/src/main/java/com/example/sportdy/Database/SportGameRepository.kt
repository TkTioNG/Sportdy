package com.example.sportdy.Database

import androidx.lifecycle.LiveData

class SportGameRepository(private val sportGameDao: SportGameDao) {
    val allSportGames: LiveData<List<SportGame>> = sportGameDao.getAll()
    val userSportGames: LiveData<List<SportGame>> = sportGameDao.getFrom("TkTioNG",System.currentTimeMillis())
    val othersSportGames: LiveData<List<SportGame>>  =sportGameDao.getNotFrom("TkTioNG",System.currentTimeMillis())
    val historySportGames: LiveData<List<SportGame>>  =sportGameDao.gethistory(System.currentTimeMillis())

    suspend fun insertSportGame(sportGame: SportGame) {
        sportGameDao.insert(sportGame)
    }
}