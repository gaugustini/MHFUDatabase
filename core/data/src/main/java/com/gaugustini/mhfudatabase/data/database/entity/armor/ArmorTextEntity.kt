package com.gaugustini.mhfudatabase.data.database.entity.armor

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "armor_text",
    primaryKeys = ["armor_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = ArmorEntity::class,
            parentColumns = ["id"],
            childColumns = ["armor_id"],
        ),
    ],
)
data class ArmorTextEntity(
    @ColumnInfo(name = "armor_id") val armorId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "description") val description: String,
)
