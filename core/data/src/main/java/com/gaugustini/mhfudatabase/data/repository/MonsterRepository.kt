package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.MonsterDao
import com.gaugustini.mhfudatabase.data.mapper.MonsterMapper
import com.gaugustini.mhfudatabase.domain.filter.MonsterFilter
import com.gaugustini.mhfudatabase.domain.model.Monster
import javax.inject.Inject
import javax.inject.Singleton

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
            damageStats = monsterDao.getHitzonesByMonsterId(monsterId, language),
            ailmentStats = monsterDao.getMonsterStatusByMonsterId(monsterId),
            itemEffectiveness = monsterDao.getMonsterItemByMonsterId(monsterId),
            rewards = monsterDao.getMonsterRewardsByMonsterId(monsterId, language),
            quests = monsterDao.getQuestsByMonsterId(monsterId, language),
        )
    }

    /**
     * Returns the list of all monsters or filtering by [MonsterFilter].
     * Note: Hitzones, ailments, item effectiveness, rewards and quests are not populated.
     */
    suspend fun getMonsterList(
        language: String,
        filter: MonsterFilter = MonsterFilter(),
    ): List<Monster> {
        return monsterDao.getMonsterList(
            language = language,
            name = filter.name,
            ecology = filter.ecology,
            type = filter.type?.name
        ).map { MonsterMapper.toModel(it) }
    }

}
