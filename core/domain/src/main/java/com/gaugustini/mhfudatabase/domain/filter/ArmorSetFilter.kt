package com.gaugustini.mhfudatabase.domain.filter

import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.ArmorSet

/**
 * Filter for [ArmorSet].
 */
data class ArmorSetFilter(
    val name: String? = null,
    val rarity: List<Int>? = null,
    val rank: Rank? = null,
    //val gender: Gender? = null, TODO: Add Gender when implemented
    val hunterType: HunterType? = null,
    val skills: List<Int>? = null,
)
