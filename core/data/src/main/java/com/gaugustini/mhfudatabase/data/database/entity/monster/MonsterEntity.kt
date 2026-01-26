package com.gaugustini.mhfudatabase.data.database.entity.monster

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "monster",
    primaryKeys = ["id"],
)
data class MonsterEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "monster_type") val monsterType: String,
    @ColumnInfo(name = "golden_smallest_min") val sizeSmallestMin: Int?,
    @ColumnInfo(name = "golden_smallest_max") val sizeSmallestMax: Int?,
    @ColumnInfo(name = "golden_largest_min") val sizeLargestMin: Int?,
    @ColumnInfo(name = "golden_largest_max") val sizeLargestMax: Int?,
)
