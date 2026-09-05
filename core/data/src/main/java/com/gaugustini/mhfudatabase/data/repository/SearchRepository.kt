package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.SearchDao
import com.gaugustini.mhfudatabase.data.mapper.ArmorMapper
import com.gaugustini.mhfudatabase.data.mapper.DecorationMapper
import com.gaugustini.mhfudatabase.data.mapper.ItemMapper
import com.gaugustini.mhfudatabase.data.mapper.LocationMapper
import com.gaugustini.mhfudatabase.data.mapper.MonsterMapper
import com.gaugustini.mhfudatabase.data.mapper.QuestMapper
import com.gaugustini.mhfudatabase.data.mapper.SkillMapper
import com.gaugustini.mhfudatabase.data.mapper.SkillTreeMapper
import com.gaugustini.mhfudatabase.data.mapper.WeaponMapper
import com.gaugustini.mhfudatabase.data.util.normalizeForSearch
import com.gaugustini.mhfudatabase.domain.model.SearchResults
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Search results.
 */
@Singleton
class SearchRepository @Inject constructor(
    private val searchDao: SearchDao,
) {

    /**
     * Returns the search results for the given query.
     */
    suspend fun search(
        query: String,
        language: String
    ): SearchResults {
        val normalizedQuery = query.normalizeForSearch()
        return SearchResults(
            armors = searchDao.searchArmor(normalizedQuery, language).map { ArmorMapper.toModel(it) },
            decorations = searchDao.searchDecoration(normalizedQuery, language)
                .map { DecorationMapper.toModel(it) },
            items = searchDao.searchItem(normalizedQuery, language).map { ItemMapper.toModel(it) },
            locations = searchDao.searchLocation(normalizedQuery, language).map { LocationMapper.toModel(it) },
            monsters = searchDao.searchMonster(normalizedQuery, language).map { MonsterMapper.toModel(it) },
            quests = searchDao.searchQuest(normalizedQuery, language).map { QuestMapper.toModel(it) },
            skillTrees = searchDao.searchSkillTree(normalizedQuery, language).map { SkillTreeMapper.toModel(it) },
            skills = searchDao.searchSkill(normalizedQuery, language).map { SkillMapper.toModel(it) },
            weapons = searchDao.searchWeapon(normalizedQuery, language).map { WeaponMapper.toModel(it) }
        )
    }

}
