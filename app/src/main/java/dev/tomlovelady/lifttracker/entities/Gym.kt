package dev.tomlovelady.lifttracker.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gym_table")
data class Gym(@ColumnInfo(name = "name") val name: String,
          @ColumnInfo(name = "type") val type: String,
          @ColumnInfo(name = "address") val address: String,
          @PrimaryKey(autoGenerate = true) val gymId: Long = 0
)