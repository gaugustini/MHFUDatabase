package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.database.dao.MonsterDao
import com.gaugustini.mhfudatabase.data.model.Monster
import com.gaugustini.mhfudatabase.data.model.MonsterDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MonsterRepository @Inject constructor(
    private val monsterDao: MonsterDao,
    userPreferences: UserPreferences,
) {
    private val currentLanguage: StateFlow<Language> = userPreferences.getLanguage()
        .stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Eagerly,
            initialValue = Language.ENGLISH
        )

    // List

    suspend fun getMonsterList(): List<Monster> {
        val language = currentLanguage.value.code
        return monsterDao.getMonsterList(language)
    }

    // Detail

    suspend fun getMonsterDetails(
        monsterId: Int,
    ): MonsterDetails {
        val language = currentLanguage.value.code
        return MonsterDetails(
            monster = monsterDao.getMonster(monsterId, language),
            damage = monsterDao.getHitzonesForMonster(monsterId, language),
            status = monsterDao.getAilmentStatusForMonster(monsterId),
            item = monsterDao.getItemUsagesForMonster(monsterId).sortedBy { it.monsterState },
            reward = monsterDao.getRewardsForMonster(monsterId, language)
        )
    }

}
