package com.gaugustini.mhfudatabase.data.database.entity.weapon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "weapon_parent",
    primaryKeys = ["weapon_id", "parent_weapon_id"],
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["id"],
            childColumns = ["weapon_id"],
        ),
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["id"],
            childColumns = ["parent_weapon_id"],
        ),
    ],
    indices = [
        Index(value = ["parent_weapon_id"]),
    ],
)
data class WeaponParentEntity(
    @ColumnInfo(name = "weapon_id") val weaponId: Int,
    @ColumnInfo(name = "parent_weapon_id") val parentWeaponId: Int,
)
