package dev.tomlovelady.lifttracker

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.tomlovelady.lifttracker.daos.GymDao
import dev.tomlovelady.lifttracker.daos.MovementDao
import dev.tomlovelady.lifttracker.daos.SessionDao
//import dev.tomlovelady.lifttracker.daos.MovementDao
//import dev.tomlovelady.lifttracker.daos.SessionDao
//import dev.tomlovelady.lifttracker.daos.SetDao
import dev.tomlovelady.lifttracker.entities.Gym
import dev.tomlovelady.lifttracker.entities.Movement
import dev.tomlovelady.lifttracker.entities.Session
import dev.tomlovelady.lifttracker.entities.Set
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Gym::class, Session::class, Movement::class], version = 4, exportSchema = false)
abstract class LiftTrackerDatabase : RoomDatabase() {

    abstract fun gymDao(): GymDao
    abstract fun movementDao(): MovementDao
    abstract fun sessionDao(): SessionDao
//    abstract fun setDao(): SetDao

    companion object {
        @Volatile
        private var INSTANCE: LiftTrackerDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): LiftTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LiftTrackerDatabase::class.java,
                    "lift_tracker_database"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}