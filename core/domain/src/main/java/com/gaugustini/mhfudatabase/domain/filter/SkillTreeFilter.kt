package com.gaugustini.mhfudatabase.domain.filter

import com.gaugustini.mhfudatabase.domain.enums.SkillCategory
import com.gaugustini.mhfudatabase.domain.model.SkillTree

/**
 * Filter for [SkillTree].
 */
data class SkillTreeFilter(
    val name: String? = null,
    val category: SkillCategory? = null,
)
