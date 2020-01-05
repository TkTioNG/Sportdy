package com.example.sportdy.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "SportGame")
data class SportGame (
    @PrimaryKey(autoGenerate = true) val gameID: Int,
    @ColumnInfo(name = "gamename") val gameName: String,
    @ColumnInfo(name = "gametype") val gameType: String,
    @ColumnInfo(name = "gamedate") val gameDate: Long,
    @ColumnInfo(name = "gametime") val gameTime: Int,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "street1") val street1: String,
    @ColumnInfo(name = "street2") val street2: String,
    @ColumnInfo(name = "area") val area: String,
    @ColumnInfo(name = "postcode") val postcode: Int,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "maxppl") val maxppl: Int,
    @ColumnInfo(name = "nowppl") val nowppl: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "hosterName") val hosterName: String
)