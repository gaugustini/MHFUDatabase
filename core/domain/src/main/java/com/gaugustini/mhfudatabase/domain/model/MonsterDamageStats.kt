package com.gaugustini.mhfudatabase.domain.model

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
