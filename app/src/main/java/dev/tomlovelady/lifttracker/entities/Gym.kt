package dev.tomlovelady.lifttracker.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gym_table")
data class Gym(@PrimaryKey(autoGenerate = true) val gymId: Long,
          @ColumnInfo(name = "name") val name: String,
          @ColumnInfo(name = "address") val address: String)