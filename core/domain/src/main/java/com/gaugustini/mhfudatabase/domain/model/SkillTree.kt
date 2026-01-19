package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.SkillCategory

/**
 * Represents a skill tree, which is a collection of related skills.
 *
 * @property id The unique identifier of the skill tree.
 * @property name The name of the skill tree (e.g., "Attack", "Health").
 * @property category The category of the skill.
 * @property skills The list of skills that belong to this skill tree.
 */
data class SkillTree(
    val id: Int,
    val name: String,
    val category: SkillCategory,
    val skills: List<Skill>,
)

/**
 * Represents a skill tree and the points in an equipment.
 */
data class SkillPoint(
    val skillTree: SkillTree,
    val points: Int,
)
