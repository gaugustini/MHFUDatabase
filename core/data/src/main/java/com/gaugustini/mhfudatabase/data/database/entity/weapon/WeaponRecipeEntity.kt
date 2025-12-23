package com.gaugustini.mhfudatabase.data.database.entity.weapon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity

@Entity(
    tableName = "weapon_recipe",
    primaryKeys = ["weapon_id", "item_id", "recipe_type"],
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["id"],
            childColumns = ["weapon_id"],
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
data class WeaponRecipeEntity(
    @ColumnInfo(name = "weapon_id") val weaponId: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "recipe_type") val recipeType: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
)
