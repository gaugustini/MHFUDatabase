package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.MonsterAilment

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
