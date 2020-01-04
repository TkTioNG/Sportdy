package com.example.sportdy.Database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SportGameViewModel(application: Application): AndroidViewModel(application) {
    private val repository: SportGameRepository
    val allSportGames: LiveData<List<SportGame>>

    init {
        val sportGameDao = SportGameDatabase.getInstance(application).sportGameDao()
        repository = SportGameRepository(sportGameDao)
        allSportGames = repository.allSportGames
    }

    fun insertSportGame(sportGame: SportGame) = viewModelScope.launch {
        repository.insertSportGame(sportGame)
    }
}