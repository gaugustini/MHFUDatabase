package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.QuestDao
import com.gaugustini.mhfudatabase.data.mapper.QuestMapper
import com.gaugustini.mhfudatabase.domain.filter.QuestFilter
import com.gaugustini.mhfudatabase.domain.model.Quest
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Quest.
 */
@Singleton
class QuestRepository @Inject constructor(
    private val questDao: QuestDao,
) {

    /**
     * Returns the quest with the given ID.
     */
    suspend fun getQuest(
        questId: Int,
        language: String,
    ): Quest {
        return QuestMapper.toModel(
            quest = questDao.getQuest(questId, language),
            location = questDao.getLocationByQuestId(questId, language),
            monsters = questDao.getMonstersByQuestId(questId, language),
        )
    }

    /**
     * Returns the list of all quests or filtering by [QuestFilter].
     * Note: Location and monsters are not populated.
     */
    suspend fun getQuestList(
        language: String,
        filter: QuestFilter = QuestFilter(),
    ): List<Quest> {
        return questDao.getQuestList(language).map { QuestMapper.toModel(it) }
    }

}
