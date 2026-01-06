package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.quest.QuestEntity
import com.gaugustini.mhfudatabase.data.database.entity.quest.QuestTextEntity

/**
 * Represents a quest entity with its associated text.
 */
data class QuestWithText(
    @Embedded
    val quest: QuestEntity,
    @Embedded
    val questText: QuestTextEntity,
)
