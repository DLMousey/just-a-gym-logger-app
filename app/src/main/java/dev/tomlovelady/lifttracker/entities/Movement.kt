package dev.tomlovelady.lifttracker.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movement_table")
class Movement(@PrimaryKey val movementId: Long,
               @ColumnInfo(name = "name") val name: String,
               @ColumnInfo(name = "muscleGroup") val muscleGroup: String)
