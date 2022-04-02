package dev.tomlovelady.lifttracker.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import dev.tomlovelady.lifttracker.entities.Session
import dev.tomlovelady.lifttracker.entities.Set

data class SessionSets(
    @Embedded val session: Session,
    @Relation(
        parentColumn = "sessionId",
        entityColumn = "setId"
    )
    val sets: List<Set>
)