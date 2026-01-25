package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.domain.enums.MonsterAilment
import com.gaugustini.mhfudatabase.domain.enums.MonsterState
import com.gaugustini.mhfudatabase.domain.enums.MonsterType
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.Monster
import com.gaugustini.mhfudatabase.domain.model.MonsterAilmentStats
import com.gaugustini.mhfudatabase.domain.model.MonsterDamageStats
import com.gaugustini.mhfudatabase.domain.model.MonsterItemEffectiveness
import com.gaugustini.mhfudatabase.domain.model.MonsterReward

object PreviewMonsterData {

    // Monster

    val monster = Monster(
        id = 1,
        name = "Monster",
        ecology = "Monster Ecology",
        description = "Monster Description",
        type = MonsterType.LARGE,
        sizeSmallestMin = 100,
        sizeSmallestMax = 200,
        sizeLargestMin = 1000,
        sizeLargestMax = 1100,
        damageStats = null,
        ailmentStats = null,
        itemEffectiveness = null,
        rewards = null,
        quests = null,
    )

    val monsterList = listOf(
        monster.copy(id = 1, name = "Monster 1"),
        monster.copy(id = 2, name = "Monster 2"),
        monster.copy(id = 3, name = "Monster 3"),
    )

    // Monster Hitzone

    val monsterHitzone = MonsterDamageStats(
        monsterId = 1,
        name = "Hitzone",
        cut = 1,
        impact = 2,
        shot = 3,
        fire = 4,
        water = 5,
        thunder = 6,
        ice = 7,
        dragon = 8,
    )

    val monsterHitzoneList = listOf(
        monsterHitzone.copy(name = "Hitzone 1"),
        monsterHitzone.copy(name = "Hitzone 2"),
        monsterHitzone.copy(name = "Hitzone 3"),
    )

    // Monster Ailment Status

    val monsterAilmentStatus = MonsterAilmentStats(
        monsterId = 1,
        type = MonsterAilment.KNOCKOUT,
        initial = 1,
        increase = 2,
        max = 3,
        duration = 4,
        damage = 5,
    )

    val monsterAilmentStatusList = listOf(
        monsterAilmentStatus.copy(type = MonsterAilment.KNOCKOUT),
        monsterAilmentStatus.copy(type = MonsterAilment.PARALYSIS),
        monsterAilmentStatus.copy(type = MonsterAilment.POISON),
        monsterAilmentStatus.copy(type = MonsterAilment.SLEEP),
    )

    // Monster Item Usage

    val monsterItemUsage = MonsterItemEffectiveness(
        monsterId = 1,
        monsterState = MonsterState.NORMAL,
        canUsePitfallTrap = true,
        canUseShockTrap = true,
        canUseFlashBomb = true,
        canUseSonicBomb = true,
        canUseDungBomb = true,
        canUseMeat = true,
    )

    val monsterItemUsageList = listOf(
        monsterItemUsage.copy(monsterState = MonsterState.NORMAL),
        monsterItemUsage.copy(monsterState = MonsterState.ENRAGED),
    )

    // Monster Reward

    val monsterReward = MonsterReward(
        item = PreviewItemData.item,
        condition = "Condition",
        rank = Rank.LOW,
        stackSize = 1,
        percentage = 10,
    )

    val monsterRewardList = listOf(
        monsterReward.copy(condition = "Condition 1"),
        monsterReward.copy(condition = "Condition 1"),
        monsterReward.copy(condition = "Condition 1"),
    )

}
