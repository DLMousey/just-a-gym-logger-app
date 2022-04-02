package dev.tomlovelady.lifttracker.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_table")
class Session(@PrimaryKey val sessionId: Long,
              @Embedded val gym: Gym,
              @ColumnInfo(name = "datetime") val datetime: Long)