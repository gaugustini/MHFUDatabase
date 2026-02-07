package com.gaugustini.mhfudatabase.domain.filter

import com.gaugustini.mhfudatabase.domain.enums.WeaponElement
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.domain.model.Weapon

/**
 * Filter for [Weapon].
 */
data class WeaponFilter(
    val name: String? = null,
    val weaponType: List<WeaponType>? = null,
    val numberOfSlots: List<Int>? = null,
    val rarity: List<Int>? = null,
    val elementType: List<WeaponElement>? = null,
)
