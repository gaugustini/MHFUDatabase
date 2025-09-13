package com.gaugustini.mhfudatabase.data.model

import com.gaugustini.mhfudatabase.data.enums.HubType
import com.gaugustini.mhfudatabase.data.enums.QuestGoalType
import com.gaugustini.mhfudatabase.data.enums.QuestType

// TODO: Add ranks
data class Quest(
    val id: Int,
    val name: String,
    val goal: String,
    val goalType: QuestGoalType,
    val client: String,
    val description: String,
    val hubType: HubType,
    val stars: Int,
    val questType: QuestType,
    val reward: Int,
    val fee: Int,
    val timeLimit: Int,
    val locationId: Int,
    val locationName: String,
)

//TODO: Add Location, remove location from Quest
data class QuestDetails(
    val quest: Quest,
    val monsters: List<Monster>,
)
