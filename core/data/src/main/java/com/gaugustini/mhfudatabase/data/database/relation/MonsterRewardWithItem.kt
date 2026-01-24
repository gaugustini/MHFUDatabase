package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterRewardEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.RewardConditionTextEntity

/**
 * Represents a monster reward entity with its associated condition, item, and text entities.
 */
data class MonsterRewardWithItem(
    @Embedded(prefix = "mr_") val monsterReward: MonsterRewardEntity,
    @Embedded(prefix = "rctxt_") val rewardConditionText: RewardConditionTextEntity,
    @Embedded val item: ItemEntity,
    @Embedded val itemText: ItemTextEntity,
)
