package dev.tomlovelady.lifttracker.repositories

import androidx.annotation.WorkerThread
import dev.tomlovelady.lifttracker.daos.MovementDao
import dev.tomlovelady.lifttracker.entities.Movement
import kotlinx.coroutines.flow.Flow

class MovementRepository(private val movementDao: MovementDao) {

    val allMovements: Flow<List<Movement>> = movementDao.getAllMovements()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(movement: Movement) {
        movementDao.insert(movement)
    }

    fun findMovementById(movementId: Long): Flow<Movement> {
        return movementDao.getMovement(movementId)
    }

    fun findMovementsByName(movementName: String): Flow<List<Movement>> {
        return movementDao.getMovement(movementName)
    }
}