package com.gaugustini.mhfudatabase.data.database.entity.armorset

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "armor_set",
    primaryKeys = ["id"],
)
data class ArmorSetEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "rarity") val rarity: Int,
    @ColumnInfo(name = "rank") val rank: String,
    @ColumnInfo(name = "hunter_type") val hunterType: String,
)
