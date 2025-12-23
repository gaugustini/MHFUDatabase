package com.gaugustini.mhfudatabase.data.database.entity.decoration

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity

@Entity(
    tableName = "decoration",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],
        ),
    ],
)
data class DecorationEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "required_slots") val requiredSlots: Int,
)
