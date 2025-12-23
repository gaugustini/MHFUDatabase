package com.gaugustini.mhfudatabase.data.database.entity.userset

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.decoration.DecorationEntity

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
    @ColumnInfo(name = "equipment_type") val equipmentType: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
)
