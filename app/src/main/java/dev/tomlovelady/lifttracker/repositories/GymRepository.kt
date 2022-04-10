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

    fun findGymById(gymId: Long): Flow<Gym> {
        return gymDao.getGym(gymId)
    }

    fun deleteGym(gym: Gym) {
        return gymDao.deleteGym(gym)
    }

    companion object {
        @Volatile private var instance: GymRepository? = null

        fun getInstance(gymDao: GymDao) =
            instance ?: synchronized(this) {
                instance ?: GymRepository(gymDao).also { instance = it }
            }
    }
}