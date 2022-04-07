package dev.tomlovelady.lifttracker.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.tomlovelady.lifttracker.entities.Movement
import kotlinx.coroutines.flow.Flow

@Dao
interface MovementDao {

    @Query("SELECT * FROM movement_table")
    fun getAllMovements(): Flow<List<Movement>>

   @Query("SELECT * FROM movement_table WHERE movementId=:id")
   fun getMovement(id: Long): Flow<Movement>

   @Query("SELECT * FROM movement_table WHERE name LIKE :name")
   fun getMovement(name: String): Flow<List<Movement>>

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insert(movement: Movement)
}