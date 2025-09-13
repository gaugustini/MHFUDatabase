package com.gaugustini.mhfudatabase.data.repository

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
        language: String = "en",
    ): SearchResults {
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
