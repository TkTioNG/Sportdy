package com.example.sportdy.Database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SportGameDao {
    @Insert
    suspend fun insert(game: SportGame)

    @Update
    suspend fun update(game: SportGame)

    @Delete
    suspend fun delete(game: SportGame)

    @Query("SELECT * FROM SportGame")
    fun getAll(): LiveData<List<SportGame>>

    @Query("SELECT * FROM SportGame WHERE gameID = :search_game_id")
    fun getOne(search_game_id:Int): LiveData<List<SportGame>>

}