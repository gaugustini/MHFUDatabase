package com.gaugustini.mhfudatabase.data.database.entity.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "location_text",
    primaryKeys = ["location_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["location_id"],
        ),
    ],
)
data class LocationTextEntity(
    @ColumnInfo(name = "location_id") val id: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
)
