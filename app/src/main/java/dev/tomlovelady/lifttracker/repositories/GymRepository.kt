package dev.tomlovelady.lifttracker.repositories

import androidx.annotation.WorkerThread
import dev.tomlovelady.lifttracker.daos.GymDao
import dev.tomlovelady.lifttracker.entities.Gym
import kotlinx.coroutines.flow.Flow

class GymRepository(private val gymDao: GymDao) {

    val allGyms: Flow<List<Gym>> = gymDao.getGyms()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(gym: Gym) {
        gymDao.insert(gym)
    }
}