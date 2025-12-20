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
 * @property quantity The quantity of the item (used for general purposes).
 * @property percentage The percentage chance of obtaining the item (used for rewards).
 */
data class Item(
    val id: Int,
    val name: String,
    val description: String,
    val rarity: Int,
    val buyPrice: Int,
    val sellPrice: Int,
    val carryMax: Int,
    val iconType: ItemIconType,
    val iconColor: ItemIconColor,
    val quantity: Int?,
    val percentage: Int?,
)
