package com.gaugustini.mhfudatabase.data.database.entity.monster

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "reward_condition_text",
    primaryKeys = ["reward_condition_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = RewardConditionEntity::class,
            parentColumns = ["id"],
            childColumns = ["reward_condition_id"],
        ),
    ],
)
data class RewardConditionTextEntity(
    @ColumnInfo(name = "reward_condition_id") val rewardConditionId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
)
