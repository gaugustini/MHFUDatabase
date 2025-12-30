package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.MonsterWithText
import com.gaugustini.mhfudatabase.domain.enums.MonsterType
import com.gaugustini.mhfudatabase.domain.model.Monster

/**
 * Mapper for Monster entities.
 */
object MonsterMapper {

    fun map(
        monster: MonsterWithText,
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
        )
    }

    fun mapList(
        monsters: List<MonsterWithText>,
    ): List<Monster> {
        return monsters.map { map(it) }
    }

}
