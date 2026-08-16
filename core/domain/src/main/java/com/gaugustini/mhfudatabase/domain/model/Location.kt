package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.GatherType
import com.gaugustini.mhfudatabase.domain.enums.Rank

/**
 * Represents a location in the game where quests take place and items can be gathered.
 *
 * @property id The unique identifier of the location.
 * @property name The name of the location.
 * @property gatheringPoints A map of items that can be gathered in this location, grouped by rank.
 * @property quests A list of quests that can be completed in this location.
 */
data class Location(
    val id: Int,
    val name: String,
    val gatheringPoints: Map<Rank, List<GatheringPoint>>?,
    val quests: List<Quest>?,
)

/**
 * Represents a point in the game where items can be gathered.
 */
data class GatheringPoint(
    val rank: Rank,
    val area: Int,
    val node: Int,
    val type: GatherType,
    val min: Int,
    val max: Int,
    val item: Item,
    val percentage: Int,
)
