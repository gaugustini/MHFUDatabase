package com.gaugustini.mhfudatabase.domain.model

/**
 * Represents a skill that can be activated by accumulating skill points.
 *
 * @property id The unique identifier of the skill.
 * @property skillTreeId The ID of the skill tree this skill belongs to.
 * @property name The name of the skill.
 * @property description A description of the skill's effects.
 * @property requiredPoints The number of points required to activate this skill.
 */
data class Skill(
    val id: Int,
    val skillTreeId: Int,
    val name: String,
    val description: String,
    val requiredPoints: Int,
)
