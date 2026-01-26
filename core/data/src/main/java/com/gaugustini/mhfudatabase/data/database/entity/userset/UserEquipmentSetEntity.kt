package com.gaugustini.mhfudatabase.data.database.entity.userset

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponEntity

@Entity(
    tableName = "user_set",
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["id"],
            childColumns = ["weapon_id"],
        ),
    ],
    indices = [
        Index(value = ["weapon_id"]),
    ],
)
data class UserEquipmentSetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "weapon_id") val weaponId: Int?,
)
