package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.DecorationDao
import com.gaugustini.mhfudatabase.data.model.Decoration
import com.gaugustini.mhfudatabase.data.model.DecorationDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DecorationRepository @Inject constructor(
    private val decorationDao: DecorationDao,
) {

    // List

    suspend fun getDecorationList(
        language: String = "en",
    ): List<Decoration> {
        return decorationDao.getDecorationList(language)
    }

    // Detail

    suspend fun getDecorationDetails(
        decorationId: Int,
        language: String = "en",
    ): DecorationDetails {
        return DecorationDetails(
            decoration = decorationDao.getDecoration(decorationId, language),
            skills = decorationDao.getSkillsForDecoration(decorationId, language),
            recipeA = decorationDao.getItemsForDecoration(decorationId, 1, language),
            recipeB = decorationDao.getItemsForDecoration(decorationId, 2, language)
        )
    }

}
