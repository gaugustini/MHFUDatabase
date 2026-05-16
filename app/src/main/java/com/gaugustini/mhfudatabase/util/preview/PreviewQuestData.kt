package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.domain.enums.HubType
import com.gaugustini.mhfudatabase.domain.enums.QuestGoal
import com.gaugustini.mhfudatabase.domain.enums.QuestGroup
import com.gaugustini.mhfudatabase.domain.enums.QuestType
import com.gaugustini.mhfudatabase.domain.model.Quest
import com.gaugustini.mhfudatabase.domain.model.QuestReward
import com.gaugustini.mhfudatabase.domain.model.QuestSupply

object PreviewQuestData {

    // Quest

    val quest = Quest(
        id = 1,
        name = "Quest",
        goal = "Quest Goal",
        goalType = QuestGoal.HUNT,
        client = "Quest Client",
        description = "Quest Description",
        group = QuestGroup.VILLAGE_1,
        hubType = HubType.VILLAGE,
        stars = 5,
        questType = QuestType.NORMAL,
        reward = 1,
        fee = 1,
        timeLimit = 1,
        location = PreviewLocationData.location,
        monsters = PreviewMonsterData.monsterList,
        rewards = null,
        supplies = null,
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

    val questReward = QuestReward(
        item = PreviewItemData.item,
        condition = "Condition",
        quantity = 1,
        percentage = 100
    )

    val questRewardList = listOf(
        questReward.copy(condition = "Condition 1"),
        questReward.copy(condition = "Condition 2"),
        questReward.copy(condition = "Condition 3"),
    )

    val questSupply = QuestSupply(
        item = PreviewItemData.item,
        quantity = 1,
        boxOrder = 1,
    )

    val questSupplyList = listOf(
        questSupply.copy(boxOrder = 1),
        questSupply.copy(boxOrder = 2),
        questSupply.copy(boxOrder = 3),
    )

}
