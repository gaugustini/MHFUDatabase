package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.MonsterDao
import com.gaugustini.mhfudatabase.data.mapper.MonsterMapper
import com.gaugustini.mhfudatabase.domain.enums.MonsterState
import com.gaugustini.mhfudatabase.domain.model.Monster
import com.gaugustini.mhfudatabase.domain.model.MonsterItemEffectiveness
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
        val monsterWithText = monsterDao.getMonster(monsterId, language)

        return MonsterMapper.toModel(
            monster = monsterWithText,
            damageStats = emptyList(),
            ailmentStats = emptyList(),
            itemEffectiveness = MonsterItemEffectiveness(
                monsterId = 1,
                monsterState = MonsterState.NORMAL,
                canUsePitfallTrap = false,
                canUseShockTrap = false,
                canUseFlashBomb = false,
                canUseSonicBomb = false,
                canUseDungBomb = false,
                canUseMeat = false,
            ),
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
        val monstersWithText = monsterDao.getMonsterList(language)

        return monstersWithText.map {
            MonsterMapper.toModel(
                monster = it,
                damageStats = emptyList(),
                ailmentStats = emptyList(),
                itemEffectiveness = MonsterItemEffectiveness(
                    monsterId = 1,
                    monsterState = MonsterState.NORMAL,
                    canUsePitfallTrap = false,
                    canUseShockTrap = false,
                    canUseFlashBomb = false,
                    canUseSonicBomb = false,
                    canUseDungBomb = false,
                    canUseMeat = false,
                ),
                rewards = emptyList(),
                quests = emptyList(),
            )
        }
    }

}
