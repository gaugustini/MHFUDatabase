package com.gaugustini.mhfudatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "armor_set",
    primaryKeys = ["id"]
)
data class ArmorSetEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "rarity") val rarity: Int,
    @ColumnInfo(name = "rank") val rank: String,
    @ColumnInfo(name = "hunter_type") val hunterType: String,
)

@Entity(
    tableName = "armor_set_text",
    primaryKeys = ["armor_set_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = ArmorSetEntity::class,
            parentColumns = ["id"],
            childColumns = ["armor_set_id"]
        )
    ]
)
data class ArmorSetTextEntity(
    @ColumnInfo(name = "armor_set_id") val id: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
)
