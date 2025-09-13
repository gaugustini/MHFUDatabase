package com.gaugustini.mhfudatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "item",
    primaryKeys = ["id"]
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

@Entity(
    tableName = "item_text",
    primaryKeys = ["item_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_id"]
        )
    ]
)
data class ItemTextEntity(
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
)

@Entity(
    tableName = "item_combination",
    primaryKeys = ["item_created_id", "item_a_id", "item_b_id"],
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_created_id"]
        ),
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_a_id"]
        ),
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_b_id"]
        )
    ],
    indices = [
        Index(value = ["item_a_id"]),
        Index(value = ["item_b_id"])
    ]
)
data class ItemCombinationEntity(
    @ColumnInfo(name = "item_created_id") val itemCreatedId: Int,
    @ColumnInfo(name = "item_a_id") val itemAId: Int,
    @ColumnInfo(name = "item_b_id") val itemBId: Int,
    @ColumnInfo(name = "combination_type") val combinationType: String,
    @ColumnInfo(name = "quantity_min") val quantityMin: Int,
    @ColumnInfo(name = "quantity_max") val quantityMax: Int,
    @ColumnInfo(name = "percentage") val percentage: Int,
)
