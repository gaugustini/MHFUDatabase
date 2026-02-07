package com.gaugustini.mhfudatabase.domain.filter

import com.gaugustini.mhfudatabase.domain.enums.HubType
import com.gaugustini.mhfudatabase.domain.enums.QuestGoal
import com.gaugustini.mhfudatabase.domain.enums.QuestType
import com.gaugustini.mhfudatabase.domain.model.Quest

/**
 * Filter for [Quest].
 */
data class QuestFilter(
    val name: String? = null,
    val hub: HubType? = null,
    //val rank: Rank? = null, TODO: Add rank when implemented
    val stars: List<Int>? = null,
    val type: QuestType? = null,
    val goal: QuestGoal? = null,
    val locations: List<Int>? = null,
)
