package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.MonsterType

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
)
