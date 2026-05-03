package com.gaugustini.mhfudatabase.data.database.entity.veggie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity

@Entity(
    tableName = "veggie_trade",
    primaryKeys = ["veggie_id", "item_traded_id"],
    foreignKeys = [
        ForeignKey(
            entity = VeggieEntity::class,
            parentColumns = ["id"],
            childColumns = ["veggie_id"],
        ),
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_traded_id"],
        ),
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_common_id"],
        ),
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_rare_id"],
        ),
    ],
    indices = [
        Index(value = ["item_traded_id"]),
        Index(value = ["item_common_id"]),
        Index(value = ["item_rare_id"]),
    ],
)
data class VeggieTradeEntity(
    @ColumnInfo(name = "veggie_id") val veggieId: Int,
    @ColumnInfo(name = "item_traded_id") val itemTradedId: Int,
    @ColumnInfo(name = "item_common_id") val itemCommonId: Int,
    @ColumnInfo(name = "item_rare_id") val itemRareId: Int,
)
