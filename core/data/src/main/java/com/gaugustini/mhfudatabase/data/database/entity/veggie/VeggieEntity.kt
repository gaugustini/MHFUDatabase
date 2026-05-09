package com.gaugustini.mhfudatabase.data.database.entity.veggie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationEntity

@Entity(
    tableName = "veggie",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["location_id"],
        ),
    ],
    indices = [
        Index(value = ["location_id"]),
    ],
)
data class VeggieEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "location_id") val locationId: Int,
    @ColumnInfo(name = "location_area") val locationArea: Int,
)
