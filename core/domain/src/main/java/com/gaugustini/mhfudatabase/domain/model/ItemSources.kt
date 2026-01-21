package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.GatherType
import com.gaugustini.mhfudatabase.domain.enums.Rank

/**
 * Represents a source of an item.
 */
sealed interface ItemSource

/**
 * Represents a location where an item can be obtained.
 */
data class GatheringSource(
    val location: Location,
    val rank: Rank,
    val type: GatherType,
    val area: Int,
) : ItemSource

/**
 * Represents a monster reward or drop item.
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
)
