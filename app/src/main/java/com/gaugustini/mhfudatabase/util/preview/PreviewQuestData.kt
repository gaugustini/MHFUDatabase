package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.data.enums.HubType
import com.gaugustini.mhfudatabase.data.enums.QuestGoalType
import com.gaugustini.mhfudatabase.data.enums.QuestType
import com.gaugustini.mhfudatabase.data.model.Quest

object PreviewQuestData {

    // Quest

    val quest = Quest(
        id = 1,
        name = "Quest",
        goal = "Quest Goal",
        goalType = QuestGoalType.HUNT,
        client = "Quest Client",
        description = "Quest Description",
        hubType = HubType.VILLAGE,
        stars = 5,
        questType = QuestType.NORMAL,
        reward = 1,
        fee = 1,
        timeLimit = 1,
        locationId = 1,
        locationName = "Location",
    )

    val questList = listOf(
        quest.copy(
            id = 1,
            name = "Quest 1",
            goalType = QuestGoalType.HUNT,
            questType = QuestType.NORMAL,
        ),
        quest.copy(
            id = 2,
            name = "Quest 2",
            goalType = QuestGoalType.SLAY,
            questType = QuestType.URGENT,
        ),
        quest.copy(
            id = 3,
            name = "Quest 3",
            goalType = QuestGoalType.GATHER,
            questType = QuestType.KEY,
        ),
        quest.copy(
            id = 4,
            name = "Quest 4",
            goalType = QuestGoalType.SPECIAL,
            questType = QuestType.SPECIAL,
        ),
    )

}
