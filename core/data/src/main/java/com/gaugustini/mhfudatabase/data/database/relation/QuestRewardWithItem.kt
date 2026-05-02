package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.common.RewardConditionTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.quest.QuestRewardEntity

/**
 * Represents a quest reward entity with its associated condition, item, and text entities.
 */
data class QuestRewardWithItem(
    @Embedded(prefix = "qr_") val questReward: QuestRewardEntity,
    @Embedded(prefix = "rctxt_") val rewardConditionText: RewardConditionTextEntity,
    @Embedded val item: ItemEntity,
    @Embedded val itemText: ItemTextEntity,
)
