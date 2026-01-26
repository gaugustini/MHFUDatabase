package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterTextEntity

/**
 * Represents a monster entity with its associated text.
 */
data class MonsterWithText(
    @Embedded val monster: MonsterEntity,
    @Embedded val monsterText: MonsterTextEntity,
)
