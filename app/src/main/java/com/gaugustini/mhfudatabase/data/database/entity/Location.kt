package com.gaugustini.mhfudatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "location",
    primaryKeys = ["id"]
)
data class LocationEntity(
    @ColumnInfo(name = "id") val id: Int,
)

@Entity(
    tableName = "location_text",
    primaryKeys = ["location_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["location_id"]
        )
    ]
)
data class LocationTextEntity(
    @ColumnInfo(name = "location_id") val id: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
)

@Entity(
    tableName = "location_item",
    primaryKeys = ["location_id", "item_id", "rank", "gather_type", "area"],
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_id"]
        )
    ],
    indices = [
        Index(value = ["item_id"])
    ]
)
data class LocationItemEntity(
    @ColumnInfo(name = "location_id") val locationId: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "rank") val rank: String,
    @ColumnInfo(name = "gather_type") val gatherType: String,
    @ColumnInfo(name = "area") val area: Int,
)
