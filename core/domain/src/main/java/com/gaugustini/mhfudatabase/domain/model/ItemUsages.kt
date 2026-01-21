package com.gaugustini.mhfudatabase.domain.model

/**
 * Represents a usage of an item in the game.
 */
data class Usage<out T>(
    val craftable: T,
    val quantity: Int,
)

/**
 * Represents all the ways a specific item can be utilized.
 *
 * @property combinations The list of item combinations that require the item to be crafted.
 * @property armors The list of armors that require the item to be crafted.
 * @property decorations The list of decorations that require the item to be crafted.
 * @property weapons The list of weapons that require the item to be crafted.
 */
data class ItemUsages(
    val combinations: List<ItemCombination>,
    val armors: List<Usage<Armor>>,
    val decorations: List<Usage<Decoration>>,
    val weapons: List<Usage<Weapon>>,
)
