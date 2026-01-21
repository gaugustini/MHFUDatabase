package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterRewardEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.RewardConditionTextEntity

/**
 * Represents a monster reward or drop item.
 */
data class MonsterRewardSource(
    @Embedded
    val monsterReward: MonsterRewardEntity,
    @Embedded
    val rewardConditionText: RewardConditionTextEntity,
    @Embedded
    val monster: MonsterEntity,
    @Embedded
    val monsterText: MonsterTextEntity,
)
