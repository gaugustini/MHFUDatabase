package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.database.dao.SearchDao
import com.gaugustini.mhfudatabase.data.model.SearchResults
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val searchDao: SearchDao,
) {

    suspend fun search(
        query: String,
        language: Language,
    ): SearchResults {
        return SearchResults(
            armors = searchDao.searchArmor(query, language.code),
            decorations = searchDao.searchDecoration(query, language.code),
            items = searchDao.searchItem(query, language.code),
            locations = searchDao.searchLocation(query, language.code),
            monsters = searchDao.searchMonster(query, language.code),
            quests = searchDao.searchQuest(query, language.code),
            skillTrees = searchDao.searchSkillTree(query, language.code),
            skills = searchDao.searchSkill(query, language.code),
            weapons = searchDao.searchWeapon(query, language.code)
        )
    }

}
