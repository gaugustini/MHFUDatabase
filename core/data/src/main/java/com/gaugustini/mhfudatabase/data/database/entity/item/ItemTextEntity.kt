package com.gaugustini.mhfudatabase.data.database.entity.item

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "item_text",
    primaryKeys = ["item_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_id"],
        ),
    ],
)
data class ItemTextEntity(
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "description") val description: String,
)
