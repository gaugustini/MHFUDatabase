package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.monster.HitzoneTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterHitzoneEntity

/**
 * Represents a monster hitzone entity with its associated text.
 */
data class MonsterHitzoneWithText(
    @Embedded val monsterHitzone: MonsterHitzoneEntity,
    @Embedded val hitzoneText: HitzoneTextEntity,
)
