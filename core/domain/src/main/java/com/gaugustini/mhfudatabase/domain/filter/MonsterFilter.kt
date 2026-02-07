package com.gaugustini.mhfudatabase.domain.filter

import com.gaugustini.mhfudatabase.domain.enums.MonsterType
import com.gaugustini.mhfudatabase.domain.model.Monster

/**
 * Filter for [Monster].
 */
data class MonsterFilter(
    val name: String? = null,
    val ecology: String? = null,
    val type: MonsterType? = null,
)
