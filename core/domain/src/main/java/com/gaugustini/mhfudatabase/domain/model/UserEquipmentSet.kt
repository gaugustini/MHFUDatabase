package com.gaugustini.mhfudatabase.domain.model

/**
 * Represents a user-created equipment set.
 *
 * @property id The unique identifier of the user equipment set.
 * @property name The name of the user equipment set.
 * @property weapon The weapon in the set.
 * @property armors The list of armor pieces in the set.
 * @property decorations The list of decorations in the set.
 */
data class UserEquipmentSet(
    val id: Int,
    val name: String,
    val weapon: Weapon?,
    val armors: List<Armor>,
    val decorations: List<Decoration>,
)
