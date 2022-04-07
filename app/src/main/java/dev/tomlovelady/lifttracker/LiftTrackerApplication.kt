package dev.tomlovelady.lifttracker

import android.app.Application
import dev.tomlovelady.lifttracker.repositories.GymRepository
import dev.tomlovelady.lifttracker.repositories.MovementRepository
import dev.tomlovelady.lifttracker.repositories.SessionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class LiftTrackerApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { LiftTrackerDatabase.getDatabase(this, applicationScope) }
    val gymRepository by lazy { GymRepository(database.gymDao()) }
    val sessionRepository by lazy { SessionRepository(database.sessionDao()) }
    val movementRepository by lazy { MovementRepository(database.movementDao()) }
}