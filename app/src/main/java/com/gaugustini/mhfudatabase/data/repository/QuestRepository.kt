package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.database.dao.QuestDao
import com.gaugustini.mhfudatabase.data.model.Quest
import com.gaugustini.mhfudatabase.data.model.QuestDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestRepository @Inject constructor(
    private val questDao: QuestDao,
) {

    // List

    suspend fun getQuestList(
        language: Language,
    ): List<Quest> {
        return questDao.getQuestList(language.code)
    }

    // Detail

    suspend fun getQuestDetails(
        questId: Int,
        language: Language,
    ): QuestDetails {
        return QuestDetails(
            quest = questDao.getQuest(questId, language.code),
            monsters = questDao.getMonstersForQuest(questId, language.code)
        )
    }

}
