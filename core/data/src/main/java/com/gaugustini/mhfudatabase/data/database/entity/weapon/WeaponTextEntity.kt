package com.gaugustini.mhfudatabase.data.database.entity.weapon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "weapon_text",
    primaryKeys = ["weapon_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["id"],
            childColumns = ["weapon_id"],
        ),
    ],
)
data class WeaponTextEntity(
    @ColumnInfo(name = "weapon_id") val weaponId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name", collate = ColumnInfo.LOCALIZED) val name: String,
    @ColumnInfo(name = "full_name", collate = ColumnInfo.LOCALIZED) val fullName: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "name_normalized", defaultValue = "''") val nameNormalized: String,
    @ColumnInfo(name = "full_name_normalized", defaultValue = "''") val fullNameNormalized: String,
)
