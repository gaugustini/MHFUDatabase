package com.gaugustini.mhfudatabase.data.database.entity.monster

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity

@Entity(
    tableName = "monster_reward",
    primaryKeys = ["monster_id", "reward_condition_id", "item_id", "rank", "stack_size"],
    foreignKeys = [
        ForeignKey(
            entity = MonsterEntity::class,
            parentColumns = ["id"],
            childColumns = ["monster_id"],
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
data class MonsterRewardEntity(
    @ColumnInfo(name = "monster_id") val monsterId: Int,
    @ColumnInfo(name = "reward_condition_id") val rewardConditionId: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "rank") val rank: String,
    @ColumnInfo(name = "stack_size") val stackSize: Int,
    @ColumnInfo(name = "percentage") val percentage: Int?,
)
