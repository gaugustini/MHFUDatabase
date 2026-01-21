package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.MonsterWithText
import com.gaugustini.mhfudatabase.domain.enums.MonsterType
import com.gaugustini.mhfudatabase.domain.model.Monster
import com.gaugustini.mhfudatabase.domain.model.MonsterAilmentStats
import com.gaugustini.mhfudatabase.domain.model.MonsterDamageStats
import com.gaugustini.mhfudatabase.domain.model.MonsterItemEffectiveness
import com.gaugustini.mhfudatabase.domain.model.MonsterReward
import com.gaugustini.mhfudatabase.domain.model.Quest

/**
 * Mapper for Monster entities.
 */
object MonsterMapper {

    fun toModel(
        monster: MonsterWithText,
        damageStats: List<MonsterDamageStats>? = null,
        ailmentStats: List<MonsterAilmentStats>? = null,
        itemEffectiveness: MonsterItemEffectiveness? = null,
        rewards: List<MonsterReward>? = null,
        quests: List<Quest>? = null,
    ): Monster {
        return Monster(
            id = monster.monster.id,
            name = monster.monsterText.name,
            ecology = monster.monsterText.ecology,
            description = monster.monsterText.description,
            type = MonsterType.fromString(monster.monster.monsterType),
            sizeSmallestMin = monster.monster.sizeSmallestMin,
            sizeSmallestMax = monster.monster.sizeSmallestMax,
            sizeLargestMin = monster.monster.sizeLargestMin,
            sizeLargestMax = monster.monster.sizeLargestMax,
            damageStats = damageStats,
            ailmentStats = ailmentStats,
            itemEffectiveness = itemEffectiveness,
            rewards = rewards,
            quests = quests,
        )
    }

}
