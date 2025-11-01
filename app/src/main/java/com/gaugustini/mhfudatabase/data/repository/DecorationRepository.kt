package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.database.dao.DecorationDao
import com.gaugustini.mhfudatabase.data.model.Decoration
import com.gaugustini.mhfudatabase.data.model.DecorationDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DecorationRepository @Inject constructor(
    private val decorationDao: DecorationDao,
    userPreferences: UserPreferences,
) {
    private val currentLanguage: StateFlow<Language> = userPreferences.getLanguage()
        .stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Eagerly,
            initialValue = Language.ENGLISH
        )

    // List

    suspend fun getDecorationList(): List<Decoration> {
        val language = currentLanguage.value.code
        return decorationDao.getDecorationList(language)
    }

    // Detail

    suspend fun getDecorationDetails(
        decorationId: Int,
    ): DecorationDetails {
        val language = currentLanguage.value.code
        return DecorationDetails(
            decoration = decorationDao.getDecoration(decorationId, language),
            skills = decorationDao.getSkillsForDecoration(decorationId, language),
            recipeA = decorationDao.getItemsForDecoration(decorationId, 1, language),
            recipeB = decorationDao.getItemsForDecoration(decorationId, 2, language)
        )
    }

}
