package com.gaugustini.mhfudatabase.data.database.entity.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity

@Entity(
    tableName = "location_item",
    primaryKeys = ["location_id", "rank", "area", "node", "item_id"],
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_id"],
        ),
    ],
    indices = [
        Index(value = ["item_id"]),
    ],
)
data class LocationItemEntity(
    @ColumnInfo(name = "location_id") val locationId: Int,
    @ColumnInfo(name = "rank") val rank: String,
    @ColumnInfo(name = "area") val area: Int,
    @ColumnInfo(name = "node") val node: Int,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "min") val min: Int,
    @ColumnInfo(name = "max") val max: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "percentage") val percentage: Int,
)
