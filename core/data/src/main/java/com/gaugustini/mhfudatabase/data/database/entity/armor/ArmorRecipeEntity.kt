package com.gaugustini.mhfudatabase.data.database.entity.armor

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity

@Entity(
    tableName = "armor_recipe",
    primaryKeys = ["armor_id", "item_id"],
    foreignKeys = [
        ForeignKey(
            entity = ArmorEntity::class,
            parentColumns = ["id"],
            childColumns = ["armor_id"],
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
data class ArmorRecipeEntity(
    @ColumnInfo(name = "armor_id") val armorId: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "quantity") val quantity: Int,
)
