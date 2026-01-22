package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterRewardEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.RewardConditionTextEntity

/**
 * Represents a monster reward or drop item.
 */
data class MonsterRewardItem(
    @Embedded
    val monsterReward: MonsterRewardEntity,
    @Embedded
    val rewardConditionText: RewardConditionTextEntity,
    @Embedded
    val item: ItemEntity,
    @Embedded
    val itemText: ItemTextEntity,
)
