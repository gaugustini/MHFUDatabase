package com.gaugustini.mhfudatabase.data.database.entity.monster

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "reward_condition",
    primaryKeys = ["id"],
)
data class RewardConditionEntity(
    @ColumnInfo(name = "id") val id: Int,
)
