package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.ItemCombinationType

/**
 * Represents an item combination recipe.
 *
 * @property itemCreated The item that is created by the combination.
 * @property itemA The first item used in the combination.
 * @property itemB The second item used in the combination.
 * @property type The type of combination (Normal, Treasure, or Alchemy).
 * @property quantityMin The minimum quantity of the created item.
 * @property quantityMax The maximum quantity of the created item.
 * @property percentage The percentage chance of a successful combination.
 */
data class ItemCombination(
    val itemCreated: Item,
    val itemA: Item,
    val itemB: Item,
    val type: ItemCombinationType,
    val quantityMin: Int,
    val quantityMax: Int,
    val percentage: Int,
)
