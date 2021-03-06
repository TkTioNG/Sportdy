package com.example.sportdy.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.text.FieldPosition

@Dao
interface SportGameDao {
    @Insert
    suspend fun insert(game: SportGame)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sportGames: List<SportGame>)

    @Update
    suspend fun update(game: SportGame)

    @Delete
    suspend fun delete(game: SportGame)

    @Query("SELECT * FROM SportGame ORDER BY gameID DESC")
    fun getAll(): LiveData<List<SportGame>>

    @Query("SELECT * FROM SportGame WHERE gameID = :search_game_id")
    fun getOne(search_game_id:Int): LiveData<List<SportGame>>

    @Query("SELECT * FROM SportGame WHERE (hosterName = :search_hoster_name OR nowppl > 1) AND gamedate >= :today_date ORDER BY gameID DESC")
    fun getFrom(search_hoster_name:String, today_date:Long): LiveData<List<SportGame>>

    @Query("SELECT * FROM SportGame WHERE hosterName != :search_hoster_name AND gamedate >= :today_date AND nowppl <= 1 ORDER BY gameID DESC")
    fun getNotFrom(search_hoster_name:String, today_date:Long): LiveData<List<SportGame>>

    @Query("SELECT * FROM SportGame WHERE gamedate < :today_date ORDER BY gameID DESC")
    fun gethistory(today_date:Long): LiveData<List<SportGame>>

    @Query("DELETE FROM SportGame WHERE gameID = :game_id")
    suspend fun deleteOne(game_id:Int)

    @Query("UPDATE SportGame SET gamename = :game_name, gametype = :game_type, gamedate = :game_date, gametime = :game_time, location = :location, street1 = :street1, street2 = :street2, area = :area, postcode = :postcode, state = :state, maxppl = :maxppl, description = :description WHERE gameID = :game_id")
    suspend fun updateOne(game_id: Int, game_name:String, game_type:String, game_date:Long, game_time:Int, location:String, street1:String, street2:String, area:String, postcode:Int, state:String, maxppl:Int, description:String)

    @Query("UPDATE SportGame SET nowppl = nowppl + 1 WHERE gameID = :game_id AND nowppl < maxppl")
    suspend fun joinGame(game_id: Int)

    @Query("UPDATE SportGame SET nowppl = nowppl - 1 WHERE gameID = :game_id")
    suspend fun unjoinGame(game_id: Int)
}