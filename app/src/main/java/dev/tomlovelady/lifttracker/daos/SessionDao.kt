package dev.tomlovelady.lifttracker.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.tomlovelady.lifttracker.entities.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Query("SELECT * FROM session_table")
    fun getSessions(): Flow<List<Session>>

    @Query("SELECT * FROM session_table WHERE sessionId=:id")
    fun getSession(id: Long): Flow<Session>

    @Query("SELECT * FROM session_table WHERE gymId=:gymId")
    fun getSessionsByGym(gymId: Long): Flow<List<Session>>

    @Query("SELECT COUNT(*) FROM session_table WHERE gymId=:gymId")
    fun countSessionsByGym(gymId: Long): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(session: Session)
}