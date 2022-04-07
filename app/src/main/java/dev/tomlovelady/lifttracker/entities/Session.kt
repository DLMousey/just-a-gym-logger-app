package dev.tomlovelady.lifttracker.entities

import androidx.room.*

@Entity(tableName = "session_table")
class Session(@PrimaryKey(autoGenerate = true) val sessionId: Long,
              @ColumnInfo(name = "gymId") val gymId: Long,
              @ColumnInfo(name = "startedAt") val startedAt: Long,
              @ColumnInfo(name = "finishedAt") val finishedAt: Long?
)