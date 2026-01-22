package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.MonsterType
import com.gaugustini.mhfudatabase.domain.enums.Rank

/**
 * Represents a monster in the game.
 *
 * @property id The unique identifier of the monster.
 * @property name The name of the monster.
 * @property ecology The ecology of the monster.
 * @property description The description of the monster in the game.
 * @property type The type of the monster (Small or Large).
 * @property sizeSmallestMin The minimum size for a small crown.
 * @property sizeSmallestMax The maximum size for a small crown.
 * @property sizeLargestMin The minimum size for a large crown.
 * @property sizeLargestMax The maximum size for a large crown.
 * @property damageStats The list of hitzones and their corresponding values for the monster.
 * @property ailmentStats The list of ailments and their corresponding resistances.
 * @property itemEffectiveness The effectiveness of certain items on the monster (e.g., pitfall trap).
 * @property rewards A map of rewards and their corresponding conditions, grouped by rank.
 * @property quests The list of quests the monster can be encountered in.
 */
data class Monster(
    val id: Int,
    val name: String,
    val ecology: String,
    val description: String,
    val type: MonsterType,
    val sizeSmallestMin: Int?,
    val sizeSmallestMax: Int?,
    val sizeLargestMin: Int?,
    val sizeLargestMax: Int?,
    val damageStats: List<MonsterDamageStats>?,
    val ailmentStats: List<MonsterAilmentStats>?,
    val itemEffectiveness: MonsterItemEffectiveness?,
    val rewards: Map<Rank, List<MonsterReward>>?,
    val quests: List<Quest>?,
)

/**
 * Represents a monster reward and its corresponding conditions.
 */
data class MonsterReward(
    val item: Item,
    val condition: String,
    val rank: Rank,
    val stackSize: Int,
    val percentage: Int?,
)
