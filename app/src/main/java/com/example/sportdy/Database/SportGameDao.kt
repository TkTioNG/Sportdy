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

    @Query("SELECT * FROM SportGame WHERE hosterName = :search_hoster_name AND gamedate >= :today_date")
    fun getFrom(search_hoster_name:String, today_date:Long): LiveData<List<SportGame>>

    @Query("SELECT * FROM SportGame WHERE hosterName != :search_hoster_name AND gamedate >= :today_date")
    fun getNotFrom(search_hoster_name:String, today_date:Long): LiveData<List<SportGame>>

    @Query("SELECT * FROM SportGame WHERE gamedate < :today_date")
    fun gethistory(today_date:Long): LiveData<List<SportGame>>

    @Query("DELETE FROM SportGame WHERE gameID = :game_id")
    fun deleteOne(game_id:Int)

}