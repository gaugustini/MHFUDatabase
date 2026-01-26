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
        return SearchResults(
            armors = searchDao.searchArmor(query, language).map { ArmorMapper.toModel(it) },
            decorations = searchDao.searchDecoration(query, language)
                .map { DecorationMapper.toModel(it) },
            items = searchDao.searchItem(query, language).map { ItemMapper.toModel(it) },
            locations = searchDao.searchLocation(query, language).map { LocationMapper.toModel(it) },
            monsters = searchDao.searchMonster(query, language).map { MonsterMapper.toModel(it) },
            quests = searchDao.searchQuest(query, language).map { QuestMapper.toModel(it) },
            skillTrees = searchDao.searchSkillTree(query, language).map { SkillTreeMapper.toModel(it) },
            skills = searchDao.searchSkill(query, language).map { SkillMapper.toModel(it) },
            weapons = searchDao.searchWeapon(query, language).map { WeaponMapper.toModel(it) }
        )
    }

}
