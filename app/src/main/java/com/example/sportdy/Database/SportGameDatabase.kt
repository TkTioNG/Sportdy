package com.example.sportdy.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SportGame::class], version = 1, exportSchema = false)
abstract class SportGameDatabase : RoomDatabase() {
    abstract fun sportGameDao(): SportGameDao

    companion object {
        @Volatile
        private var INSTANCE: SportGameDatabase? = null

        fun getInstance(context: Context): SportGameDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SportGameDatabase::class.java,
                        "sport_game_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}