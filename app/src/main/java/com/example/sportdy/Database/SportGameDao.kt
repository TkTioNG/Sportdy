package com.example.sportdy.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.text.FieldPosition

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
    suspend fun deleteOne(game_id:Int)

    @Query("UPDATE SportGame SET gamename = :game_name, gametype = :game_type, gamedate = :game_date, gametime = :game_time, location = :location, street1 = :street1, street2 = :street2, area = :area, postcode = :postcode, state = :state, maxppl = :maxppl, description = :description WHERE gameID = :game_id")
    suspend fun updateOne(game_id: Int, game_name:String, game_type:String, game_date:Long, game_time:Int, location:String, street1:String, street2:String, area:String, postcode:Int, state:String, maxppl:Int, description:String)
}