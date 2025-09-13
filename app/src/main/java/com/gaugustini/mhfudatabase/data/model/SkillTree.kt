package com.gaugustini.mhfudatabase.data.model

import com.gaugustini.mhfudatabase.data.enums.SkillCategory

data class SkillTree(
    val id: Int,
    val name: String,
    val category: SkillCategory,
)

data class SkillTreeDetails(
    val skillTree: SkillTree,
    val skills: List<Skill>
)

data class SkillTreePoints(
    val id: Int,
    val name: String,
    val pointValue: Int,
)
