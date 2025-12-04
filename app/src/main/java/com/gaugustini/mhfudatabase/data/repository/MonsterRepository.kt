package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.database.dao.MonsterDao
import com.gaugustini.mhfudatabase.data.model.Monster
import com.gaugustini.mhfudatabase.data.model.MonsterDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MonsterRepository @Inject constructor(
    private val monsterDao: MonsterDao,
) {

    // List

    suspend fun getMonsterList(
        language: Language,
    ): List<Monster> {
        return monsterDao.getMonsterList(language.code)
    }

    // Detail

    suspend fun getMonsterDetails(
        monsterId: Int,
        language: Language,
    ): MonsterDetails {
        return MonsterDetails(
            monster = monsterDao.getMonster(monsterId, language.code),
            damage = monsterDao.getHitzonesForMonster(monsterId, language.code),
            status = monsterDao.getAilmentStatusForMonster(monsterId),
            item = monsterDao.getItemUsagesForMonster(monsterId),
            reward = monsterDao.getRewardsForMonster(monsterId, language.code),
            quest = monsterDao.getQuestsForMonster(monsterId, language.code),
        )
    }

}
