package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.MonsterDao
import com.gaugustini.mhfudatabase.data.mapper.MonsterMapper
import com.gaugustini.mhfudatabase.domain.model.Monster
import javax.inject.Inject
import javax.inject.Singleton

//TODO: Add monster remain data (Ailments, Hitzones, ...)
/**
 * Data repository for Monster.
 */
@Singleton
class MonsterRepository @Inject constructor(
    private val monsterDao: MonsterDao,
) {

    /**
     * Returns the monster with the given ID.
     */
    suspend fun getMonster(
        monsterId: Int,
        language: String,
    ): Monster {
        return MonsterMapper.toModel(
            monster = monsterDao.getMonster(monsterId, language),
            damageStats = emptyList(),
            ailmentStats = emptyList(),
            itemEffectiveness = null,
            rewards = emptyList(),
            quests = emptyList(),
        )
    }

    /**
     * Returns the list of all monsters.
     */
    suspend fun getMonsterList(
        language: String,
    ): List<Monster> {
        return monsterDao.getMonsterList(language).map { MonsterMapper.toModel(it) }
    }

}
