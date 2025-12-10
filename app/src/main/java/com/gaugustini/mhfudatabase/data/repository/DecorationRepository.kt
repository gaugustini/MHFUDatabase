package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
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
        language: Language,
    ): List<Decoration> {
        return decorationDao.getDecorationList(language.code)
    }

    // Detail

    suspend fun getDecorationDetails(
        decorationId: Int,
        language: Language,
    ): DecorationDetails {
        return DecorationDetails(
            decoration = decorationDao.getDecoration(decorationId, language.code),
            skills = decorationDao.getSkillsForDecoration(decorationId, language.code),
            recipeA = decorationDao.getItemsForDecoration(decorationId, 1, language.code),
            recipeB = decorationDao.getItemsForDecoration(decorationId, 2, language.code)
        )
    }

    // User Equipment Set

    suspend fun getDecorationListForUserEquipmentSet(
        availableSlots: Int,
        language: Language,
    ): List<Decoration> {
        return decorationDao.getDecorationListForUserEquipmentSet(availableSlots, language.code)
    }

}
