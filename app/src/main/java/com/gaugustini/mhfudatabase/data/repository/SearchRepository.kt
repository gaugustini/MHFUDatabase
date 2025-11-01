package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.database.dao.SearchDao
import com.gaugustini.mhfudatabase.data.model.SearchResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val searchDao: SearchDao,
    userPreferences: UserPreferences
) {
    private val currentLanguage: StateFlow<Language> = userPreferences.getLanguage()
        .stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Eagerly,
            initialValue = Language.ENGLISH
        )

    suspend fun search(
        query: String,
    ): SearchResults {
        val language = currentLanguage.value.code
        return SearchResults(
            armors = searchDao.searchArmor(query, language),
            decorations = searchDao.searchDecoration(query, language),
            items = searchDao.searchItem(query, language),
            locations = searchDao.searchLocation(query, language),
            monsters = searchDao.searchMonster(query, language),
            quests = searchDao.searchQuest(query, language),
            skillTrees = searchDao.searchSkillTree(query, language),
            skills = searchDao.searchSkill(query, language),
            weapons = searchDao.searchWeapon(query, language)
        )
    }

}
