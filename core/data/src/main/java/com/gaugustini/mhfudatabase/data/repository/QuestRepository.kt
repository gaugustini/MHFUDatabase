package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.QuestDao
import com.gaugustini.mhfudatabase.data.mapper.QuestMapper
import com.gaugustini.mhfudatabase.domain.model.Quest
import javax.inject.Inject
import javax.inject.Singleton

//TODO: Add quest remain data (monsters, location)
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
        val questWithText = questDao.getQuest(questId, language)

        return QuestMapper.map(questWithText)
    }

    /**
     * Returns the list of all quests.
     */
    suspend fun getQuestList(
        language: String,
    ): List<Quest> {
        val questsWithText = questDao.getQuestList(language)

        return QuestMapper.mapList(questsWithText)
    }

}
