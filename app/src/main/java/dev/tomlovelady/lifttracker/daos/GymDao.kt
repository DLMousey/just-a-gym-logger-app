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

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(gym: Gym): Long

//    @Update
//    suspend fun update(gym: Gym)

    @Query("DELETE FROM gym_table")
    fun deleteAll()

    @Delete
    fun deleteGym(gym: Gym)
}