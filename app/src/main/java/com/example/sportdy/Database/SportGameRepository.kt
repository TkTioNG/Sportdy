package com.example.sportdy.Database

import androidx.lifecycle.LiveData

class SportGameRepository(private val sportGameDao: SportGameDao) {
    val allSportGames: LiveData<List<SportGame>> = sportGameDao.getAll()

    suspend fun insertSportGame(sportGame: SportGame) {
        sportGameDao.insert(sportGame)
    }
}