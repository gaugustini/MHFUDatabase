package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterRewardEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.RewardConditionTextEntity

/**
 * Represents a monster reward entity with its associated condition, monster, and text entities.
 */
data class MonsterRewardWithMonster(
    @Embedded(prefix = "mr_") val monsterReward: MonsterRewardEntity,
    @Embedded(prefix = "rctxt_") val rewardConditionText: RewardConditionTextEntity,
    @Embedded val monster: MonsterEntity,
    @Embedded val monsterText: MonsterTextEntity,
)
