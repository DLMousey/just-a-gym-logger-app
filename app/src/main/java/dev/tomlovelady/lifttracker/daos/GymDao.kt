package dev.tomlovelady.lifttracker.daos

import androidx.room.*
import dev.tomlovelady.lifttracker.entities.Gym
import kotlinx.coroutines.flow.Flow

@Dao
interface GymDao {

    @Query("SELECT * FROM gym_table")
    fun getGyms(): Flow<List<Gym>>

    @Query("SELECT * FROM gym_table WHERE gymId=:id")
    fun getGym(id: Long): Flow<Gym>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gym: Gym)

//    @Update
//    suspend fun update(gym: Gym)

    @Query("DELETE FROM gym_table")
    fun deleteAll()
}