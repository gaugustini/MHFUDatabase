package com.gaugustini.mhfudatabase.data.database.entity.monster

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "hitzone",
    primaryKeys = ["id"],
)
data class HitzoneEntity(
    @ColumnInfo(name = "id") val id: Int,
)
