package com.gaugustini.mhfudatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.gaugustini.mhfudatabase.data.enums.EquipmentType

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

@Entity(
    tableName = "user_set_decoration",
    primaryKeys = ["user_set_id", "decoration_id", "equipment_type"],
    foreignKeys = [
        ForeignKey(
            entity = UserEquipmentSetEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_set_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = DecorationEntity::class,
            parentColumns = ["id"],
            childColumns = ["decoration_id"],
        ),
    ],
    indices = [
        Index(value = ["decoration_id"]),
    ],
)
data class UserEquipmentSetDecorationEntity(
    @ColumnInfo(name = "user_set_id") val userSetId: Int,
    @ColumnInfo(name = "decoration_id") val decorationId: Int,
    @ColumnInfo(name = "equipment_type") val equipmentType: EquipmentType,
    @ColumnInfo(name = "quantity") val quantity: Int,
)
