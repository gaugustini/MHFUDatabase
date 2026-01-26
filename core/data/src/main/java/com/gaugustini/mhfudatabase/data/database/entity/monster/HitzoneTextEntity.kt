package com.gaugustini.mhfudatabase.data.database.entity.monster

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "hitzone_text",
    primaryKeys = ["hitzone_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = HitzoneEntity::class,
            parentColumns = ["id"],
            childColumns = ["hitzone_id"],
        ),
    ],
)
data class HitzoneTextEntity(
    @ColumnInfo(name = "hitzone_id") val hitzoneId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
)
