package com.gaugustini.mhfudatabase.domain.filter

import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.domain.model.SkillTree

/**
 * Filter for [Decoration].
 */
data class DecorationFilter(
    val name: String? = null,
    val maxAvailableSlots: Int? = null,
    val numberOfSlots: List<Int>? = null,
    val skills: List<SkillTree>? = null,
)
