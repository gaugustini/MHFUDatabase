package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.GatherType
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.ItemIconType
import com.gaugustini.mhfudatabase.domain.enums.Rank

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

/**
 * Represents a source of an item.
 */
sealed interface ItemSource

/**
 * Represents a location where an item can be obtained.
 *
 * @property location The location where the item can be obtained.
 * @property rank The rank where it is possible to obtain the item.
 * @property type The gathering type to obtain the item (e.g., Gather, Mining,...).
 * @property area The area number of the location.
 */
data class GatheringSource(
    val location: Location,
    val rank: Rank,
    val type: GatherType,
    val area: Int,
) : ItemSource

/**
 * Represents a monster reward or drop item.
 *
 * @property monster The monster that drops the item.
 * @property condition The condition required to receive the reward (e.g., "Break Wings").
 * @property rank The rank of the monster required to receive the reward.
 * @property stackSize The number of items in a stack reward.
 * @property percentage The percentage chance of receiving the reward.
 */
data class MonsterSource(
    val monster: Monster,
    val condition: String,
    val rank: Rank,
    val stackSize: Int,
    val percentage: Int?,
) : ItemSource

/**
 * Represents all the ways a specific item can be obtained.
 *
 * @property combinations The list of item combinations where the result is the item.
 * @property locations The list of locations where the item can be obtained.
 * @property monsterRewards The list of monsters that drop the item.
 */
data class ItemSources(
    val combinations: List<ItemCombination>,
    val locations: List<GatheringSource>,
    val monsterRewards: List<MonsterSource>,
) {
    fun isEmpty(): Boolean {
        return combinations.isEmpty() &&
                locations.isEmpty() &&
                monsterRewards.isEmpty()
    }
}

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
) {
    fun isEmpty(): Boolean {
        return combinations.isEmpty() &&
                armors.isEmpty() &&
                decorations.isEmpty() &&
                weapons.isEmpty()
    }
}
