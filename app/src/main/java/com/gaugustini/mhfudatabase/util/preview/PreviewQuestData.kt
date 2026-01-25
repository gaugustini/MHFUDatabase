package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.domain.enums.HubType
import com.gaugustini.mhfudatabase.domain.enums.QuestGoal
import com.gaugustini.mhfudatabase.domain.enums.QuestType
import com.gaugustini.mhfudatabase.domain.model.Quest

object PreviewQuestData {

    // Quest

    val quest = Quest(
        id = 1,
        name = "Quest",
        goal = "Quest Goal",
        goalType = QuestGoal.HUNT,
        client = "Quest Client",
        description = "Quest Description",
        hubType = HubType.VILLAGE,
        stars = 5,
        questType = QuestType.NORMAL,
        reward = 1,
        fee = 1,
        timeLimit = 1,
        location = null,
        monsters = null,
    )

    val questList = listOf(
        quest.copy(
            id = 1,
            name = "Quest 1",
            goalType = QuestGoal.HUNT,
            questType = QuestType.NORMAL,
        ),
        quest.copy(
            id = 2,
            name = "Quest 2",
            goalType = QuestGoal.SLAY,
            questType = QuestType.URGENT,
        ),
        quest.copy(
            id = 3,
            name = "Quest 3",
            goalType = QuestGoal.GATHER,
            questType = QuestType.KEY,
        ),
        quest.copy(
            id = 4,
            name = "Quest 4",
            goalType = QuestGoal.SPECIAL,
            questType = QuestType.SPECIAL,
        ),
    )

}
