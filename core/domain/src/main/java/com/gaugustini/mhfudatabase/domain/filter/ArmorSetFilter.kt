package com.gaugustini.mhfudatabase.domain.filter

import com.gaugustini.mhfudatabase.domain.enums.Gender
import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.ArmorSet
import com.gaugustini.mhfudatabase.domain.model.SkillTree

/**
 * Filter for [ArmorSet].
 */
data class ArmorSetFilter(
    val name: String? = null,
    val rarity: List<Int>? = null,
    val rank: Rank? = null,
    val gender: Gender? = null,
    val hunterType: HunterType? = null,
    val skills: List<SkillTree>? = null,
)
