package com.gaugustini.mhfudatabase.data.database.entity.quest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.RewardConditionEntity

@Entity(
    tableName = "quest_reward",
    primaryKeys = ["quest_id", "reward_condition_id", "item_id", "quantity"],
    foreignKeys = [
        ForeignKey(
            entity = QuestEntity::class,
            parentColumns = ["id"],
            childColumns = ["quest_id"],
        ),
        ForeignKey(
            entity = RewardConditionEntity::class,
            parentColumns = ["id"],
            childColumns = ["reward_condition_id"],
        ),
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_id"],
        ),
    ],
    indices = [
        Index(value = ["reward_condition_id"]),
        Index(value = ["item_id"]),
    ],
)
data class QuestRewardEntity(
    @ColumnInfo(name = "quest_id") val questId: Int,
    @ColumnInfo(name = "reward_condition_id") val rewardConditionId: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "percentage") val percentage: Int,
)
