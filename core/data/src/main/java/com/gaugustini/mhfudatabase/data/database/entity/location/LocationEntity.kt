package com.gaugustini.mhfudatabase.data.database.entity.location

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "location",
    primaryKeys = ["id"],
)
data class LocationEntity(
    @ColumnInfo(name = "id") val id: Int,
)
