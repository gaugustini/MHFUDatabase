package com.gaugustini.mhfudatabase.data.database.entity.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity

@Entity(
    tableName = "location_item",
    primaryKeys = ["location_id", "item_id", "rank", "gather_type", "area"],
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
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "rank") val rank: String,
    @ColumnInfo(name = "gather_type") val gatherType: String,
    @ColumnInfo(name = "area") val area: Int,
)
