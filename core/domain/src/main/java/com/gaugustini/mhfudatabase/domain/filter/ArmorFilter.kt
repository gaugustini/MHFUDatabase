package com.gaugustini.mhfudatabase.domain.filter

import com.gaugustini.mhfudatabase.domain.enums.EquipmentType
import com.gaugustini.mhfudatabase.domain.enums.Gender
import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.domain.model.Armor

/**
 * Filter for [Armor].
 */
data class ArmorFilter(
    val name: String? = null,
    val type: EquipmentType? = null,
    val numberOfSlots: List<Int>? = null,
    val rarity: List<Int>? = null,
    val gender: Gender? = null,
    val hunterType: HunterType? = null,
    val skills: List<Int>? = null,
)
