package com.gaugustini.mhfudatabase.data.database.entity.item

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "item",
    primaryKeys = ["id"],
)
data class ItemEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "rarity") val rarity: Int,
    @ColumnInfo(name = "buy_price") val buyPrice: Int?,
    @ColumnInfo(name = "sell_price") val sellPrice: Int,
    @ColumnInfo(name = "carry_max") val carryMax: Int,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "icon_type") val iconType: String,
    @ColumnInfo(name = "icon_color") val iconColor: String,
)
