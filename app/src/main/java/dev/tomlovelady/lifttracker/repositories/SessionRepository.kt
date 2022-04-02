package dev.tomlovelady.lifttracker.repositories

import androidx.annotation.WorkerThread
import dev.tomlovelady.lifttracker.daos.SessionDao
import dev.tomlovelady.lifttracker.entities.Session
import kotlinx.coroutines.flow.Flow

class SessionRepository(private val sessionDao: SessionDao) {

    val allSessions: Flow<List<Session>> = sessionDao.getSessions()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(session: Session) {
        sessionDao.insert(session)
    }

    fun findSessionsByGym(gymId: Long): Flow<List<Session>> {
        return sessionDao.getSessionsByGym(gymId)
    }

    fun countSessionsByGym(gymId: Long): Flow<Int> {
        return sessionDao.countSessionsByGym(gymId)
    }
}