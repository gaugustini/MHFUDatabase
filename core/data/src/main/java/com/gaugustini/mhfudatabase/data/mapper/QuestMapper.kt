package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.LocationWithText
import com.gaugustini.mhfudatabase.data.database.relation.MonsterWithText
import com.gaugustini.mhfudatabase.data.database.relation.QuestWithText
import com.gaugustini.mhfudatabase.domain.enums.HubType
import com.gaugustini.mhfudatabase.domain.enums.QuestGoal
import com.gaugustini.mhfudatabase.domain.enums.QuestType
import com.gaugustini.mhfudatabase.domain.model.Quest

/**
 * Mapper for Quest entities.
 */
object QuestMapper {

    fun toModel(
        quest: QuestWithText,
        location: LocationWithText? = null,
        monsters: List<MonsterWithText>? = null,
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
            location = location?.let { LocationMapper.toModel(it) },
            monsters = monsters?.map { MonsterMapper.toModel(it) },
        )
    }

}
