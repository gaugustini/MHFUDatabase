package com.gaugustini.mhfudatabase.data.database.entity.decoration

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity

@Entity(
    tableName = "decoration_recipe",
    primaryKeys = ["decoration_id", "item_id", "recipe_variant"],
    foreignKeys = [
        ForeignKey(
            entity = DecorationEntity::class,
            parentColumns = ["id"],
            childColumns = ["decoration_id"],
        ),
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
data class DecorationRecipeEntity(
    @ColumnInfo(name = "decoration_id") val decorationId: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "recipe_variant") val variant: Int,
    @ColumnInfo(name = "quantity") val quantity: Int,
)
