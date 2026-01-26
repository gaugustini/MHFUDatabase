package com.gaugustini.mhfudatabase.data.database.entity.userset

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.armor.ArmorEntity

@Entity(
    tableName = "user_set_armor",
    primaryKeys = ["user_set_id", "armor_id"],
    foreignKeys = [
        ForeignKey(
            entity = UserEquipmentSetEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_set_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = ArmorEntity::class,
            parentColumns = ["id"],
            childColumns = ["armor_id"],
        ),
    ],
    indices = [
        Index(value = ["armor_id"]),
    ],
)
data class UserEquipmentSetArmorEntity(
    @ColumnInfo(name = "user_set_id") val userSetId: Int,
    @ColumnInfo(name = "armor_id") val armorId: Int,
)
