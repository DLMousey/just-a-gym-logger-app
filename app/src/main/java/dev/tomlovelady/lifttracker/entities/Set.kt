package dev.tomlovelady.lifttracker.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "set_table")
class Set(@PrimaryKey val setId: Long,
          @Embedded val movement: Movement,
          @ColumnInfo(name = "repetitions") val repetitions: Int,
          @ColumnInfo(name = "weight") val weight: Float,
          @ColumnInfo(name = "weightUnit") val weightUnit: String
          )