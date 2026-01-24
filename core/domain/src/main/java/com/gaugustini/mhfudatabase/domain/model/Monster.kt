package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.MonsterAilment
import com.gaugustini.mhfudatabase.domain.enums.MonsterState
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
 * Represents a monster's hitzone, detailing its weaknesses to different damage types.
 *
 * @property monsterId The unique identifier of the monster.
 * @property name The name of the hitzone (e.g., "Head", "Tail", "Wings").
 * @property cut The weakness to cutting damage.
 * @property impact The weakness to impact damage.
 * @property shot The weakness to shot damage.
 * @property fire The weakness to fire damage.
 * @property water The weakness to water damage.
 * @property thunder The weakness to thunder damage.
 * @property ice The weakness to ice damage.
 * @property dragon The weakness to dragon damage.
 */
data class MonsterDamageStats(
    val monsterId: Int,
    val name: String,
    val cut: Int,
    val impact: Int,
    val shot: Int,
    val fire: Int,
    val water: Int,
    val thunder: Int,
    val ice: Int,
    val dragon: Int,
)

/**
 * Represents a monster's resistance and tolerance to a specific ailment.
 *
 * @property monsterId The unique identifier of the monster.
 * @property type The type of ailment (e.g., Poison, Paralysis, Sleep).
 * @property initial The initial tolerance the monster has to the ailment.
 * @property increase The amount the tolerance increases each time the ailment is applied.
 * @property max The maximum tolerance the monster can have for the ailment.
 * @property duration The duration of the ailment in seconds.
 * @property damage The damage dealt by the ailment (if applicable).
 */
data class MonsterAilmentStats(
    val monsterId: Int,
    val type: MonsterAilment,
    val initial: Int,
    val increase: Int,
    val max: Int,
    val duration: Int,
    val damage: Int,
)

/**
 * Represents the effectiveness of certain items on a monster in different states.
 *
 * @property monsterId The unique identifier of the monster.
 * @property monsterState The state of the monster (e.g., Normal, Enraged).
 * @property canUsePitfallTrap Whether a pitfall trap can be used.
 * @property canUseShockTrap Whether a shock trap can be used.
 * @property canUseFlashBomb Whether a flash bomb can be used.
 * @property canUseSonicBomb Whether a sonic bomb can be used.
 * @property canUseDungBomb Whether a dung bomb can be used.
 * @property canUseMeat Whether meat can be used.
 */
data class MonsterItemEffectiveness(
    val monsterId: Int,
    val monsterState: MonsterState,
    val canUsePitfallTrap: Boolean,
    val canUseShockTrap: Boolean,
    val canUseFlashBomb: Boolean,
    val canUseSonicBomb: Boolean,
    val canUseDungBomb: Boolean,
    val canUseMeat: Boolean,
)

/**
 * Represents a monster reward and its corresponding conditions.
 *
 * @property item The item rewarded.
 * @property condition The condition required to receive the reward (e.g., "Break Wings").
 * @property rank The rank of the monster required to receive the reward.
 * @property stackSize The number of items in a stack reward.
 * @property percentage The percentage chance of receiving the reward.
 */
data class MonsterReward(
    val item: Item,
    val condition: String,
    val rank: Rank,
    val stackSize: Int,
    val percentage: Int?,
)
