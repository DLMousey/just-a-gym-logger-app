package dev.tomlovelady.lifttracker.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import dev.tomlovelady.lifttracker.entities.Gym
import dev.tomlovelady.lifttracker.entities.Session

data class SessionGym (
    @Embedded val session: Session,
    @Relation(
        parentColumn = "sessionId",
        entityColumn = "gymId"
    )
    val gym: Gym
)