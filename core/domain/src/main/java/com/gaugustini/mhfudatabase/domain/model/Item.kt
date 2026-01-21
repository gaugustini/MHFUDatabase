package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.ItemIconType

/**
 * Represents an item in the game.
 *
 * @property id The unique identifier of the item.
 * @property name The name of the item.
 * @property description The description of the item in the game.
 * @property rarity The rarity of the item.
 * @property buyPrice The price to buy the item.
 * @property sellPrice The price to sell the item.
 * @property carryMax The maximum number of this item that can be carried.
 * @property iconType The type of icon for the item.
 * @property iconColor The color of the icon for the item.
 * @property sources Where the item can be obtained.
 * @property usages Where the item can be used.
 */
data class Item(
    val id: Int,
    val name: String,
    val description: String,
    val rarity: Int,
    val buyPrice: Int?,
    val sellPrice: Int,
    val carryMax: Int,
    val iconType: ItemIconType,
    val iconColor: ItemIconColor,
    val sources: ItemSources?,
    val usages: ItemUsages?,
)

/**
 * Represents a item and quantity used to craft or upgrade an equipment.
 */
data class ItemQuantity(
    val item: Item,
    val quantity: Int,
)
