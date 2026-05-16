package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.ItemWithText
import com.gaugustini.mhfudatabase.data.database.relation.LocationWithText
import com.gaugustini.mhfudatabase.data.database.relation.MonsterWithText
import com.gaugustini.mhfudatabase.data.database.relation.QuestRewardWithItem
import com.gaugustini.mhfudatabase.data.database.relation.QuestSupplyWithItem
import com.gaugustini.mhfudatabase.data.database.relation.QuestWithText
import com.gaugustini.mhfudatabase.domain.enums.HubType
import com.gaugustini.mhfudatabase.domain.enums.QuestGoal
import com.gaugustini.mhfudatabase.domain.enums.QuestGroup
import com.gaugustini.mhfudatabase.domain.enums.QuestType
import com.gaugustini.mhfudatabase.domain.model.Quest
import com.gaugustini.mhfudatabase.domain.model.QuestReward
import com.gaugustini.mhfudatabase.domain.model.QuestSupply

/**
 * Mapper for Quest entities.
 */
object QuestMapper {

    fun toModel(
        quest: QuestWithText,
        location: LocationWithText? = null,
        monsters: List<MonsterWithText>? = null,
        rewards: List<QuestRewardWithItem>? = null,
        supplies: List<QuestSupplyWithItem>? = null,
    ): Quest {
        return Quest(
            id = quest.quest.id,
            name = quest.questText.name,
            goal = quest.questText.goal,
            client = quest.questText.client,
            description = quest.questText.description,
            group = QuestGroup.fromString(quest.quest.group),
            questType = QuestType.fromString(quest.quest.questType),
            goalType = QuestGoal.fromString(quest.quest.goalType),
            hubType = HubType.fromString(quest.quest.hubType),
            stars = quest.quest.stars,
            reward = quest.quest.reward,
            fee = quest.quest.fee,
            timeLimit = quest.quest.timeLimit,
            location = location?.let { LocationMapper.toModel(it) },
            monsters = monsters?.map { MonsterMapper.toModel(it) },
            rewards = rewards?.map { toQuestReward(it) },
            supplies = supplies?.map { toQuestSupply(it) },
        )
    }

    fun toQuestReward(
        questReward: QuestRewardWithItem,
    ): QuestReward {
        return QuestReward(
            item = ItemMapper.toModel(ItemWithText(questReward.item, questReward.itemText)),
            condition = questReward.rewardConditionText.name,
            quantity = questReward.questReward.quantity,
            percentage = questReward.questReward.percentage,
        )
    }

    fun toQuestSupply(
        questSupply: QuestSupplyWithItem,
    ): QuestSupply {
        return QuestSupply(
            item = ItemMapper.toModel(ItemWithText(questSupply.item, questSupply.itemText)),
            quantity = questSupply.questSupply.quantity,
            boxOrder = questSupply.questSupply.boxOrder,
        )
    }

}
