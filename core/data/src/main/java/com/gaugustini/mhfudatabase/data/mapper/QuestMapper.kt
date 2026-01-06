package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.QuestWithText
import com.gaugustini.mhfudatabase.domain.enums.HubType
import com.gaugustini.mhfudatabase.domain.enums.QuestGoal
import com.gaugustini.mhfudatabase.domain.enums.QuestType
import com.gaugustini.mhfudatabase.domain.model.Location
import com.gaugustini.mhfudatabase.domain.model.Quest

/**
 * Mapper for Quest entities.
 */
object QuestMapper {

    fun map(
        quest: QuestWithText,
    ): Quest {
        return Quest(
            id = quest.quest.id,
            name = quest.questText.name,
            goal = quest.questText.goal,
            client = quest.questText.client,
            description = quest.questText.description,
            questType = QuestType.fromString(quest.quest.questType),
            goalType = QuestGoal.fromString(quest.quest.goalType),
            hubType = HubType.fromString(quest.quest.hubType),
            stars = quest.quest.stars,
            reward = quest.quest.reward,
            fee = quest.quest.fee,
            timeLimit = quest.quest.timeLimit,
            location = Location(quest.quest.locationId, "", mapOf()),
            monsters = emptyList(),
        )
    }

    fun mapList(
        quests: List<QuestWithText>,
    ): List<Quest> {
        return quests.map { map(it) }
    }

}
