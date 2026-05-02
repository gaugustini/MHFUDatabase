package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.common.RewardConditionTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.quest.QuestEntity
import com.gaugustini.mhfudatabase.data.database.entity.quest.QuestRewardEntity
import com.gaugustini.mhfudatabase.data.database.entity.quest.QuestTextEntity

/**
 * Represents a quest reward entity with its associated condition, quest, and text entities.
 */
data class QuestRewardWithQuest(
    @Embedded(prefix = "qr_") val questReward: QuestRewardEntity,
    @Embedded(prefix = "rctxt_") val rewardConditionText: RewardConditionTextEntity,
    @Embedded val quest: QuestEntity,
    @Embedded val questText: QuestTextEntity,
)
