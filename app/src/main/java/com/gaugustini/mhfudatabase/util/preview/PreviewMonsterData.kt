package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.data.enums.ItemIconColor
import com.gaugustini.mhfudatabase.data.enums.ItemIconType
import com.gaugustini.mhfudatabase.data.enums.MonsterStateType
import com.gaugustini.mhfudatabase.data.enums.MonsterType
import com.gaugustini.mhfudatabase.data.enums.Rank
import com.gaugustini.mhfudatabase.data.enums.StatusType
import com.gaugustini.mhfudatabase.data.model.AilmentStatus
import com.gaugustini.mhfudatabase.data.model.Hitzone
import com.gaugustini.mhfudatabase.data.model.ItemUsage
import com.gaugustini.mhfudatabase.data.model.Monster
import com.gaugustini.mhfudatabase.data.model.MonsterReward

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
    )

    val monsterList = listOf(
        monster.copy(id = 1, name = "Monster 1"),
        monster.copy(id = 2, name = "Monster 2"),
        monster.copy(id = 3, name = "Monster 3"),
    )

    // Monster Hitzone

    val monsterHitzone = Hitzone(
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

    val monsterAilmentStatus = AilmentStatus(
        type = StatusType.KNOCKOUT,
        initial = 1,
        increase = 2,
        max = 3,
        duration = 4,
        damage = 5,
    )

    val monsterAilmentStatusList = listOf(
        monsterAilmentStatus.copy(type = StatusType.KNOCKOUT),
        monsterAilmentStatus.copy(type = StatusType.PARALYSIS),
        monsterAilmentStatus.copy(type = StatusType.POISON),
        monsterAilmentStatus.copy(type = StatusType.SLEEP),
    )

    // Monster Item Usage

    val monsterItemUsage = ItemUsage(
        monsterState = MonsterStateType.NORMAL,
        canUsePitfallTrap = true,
        pitfallDuration = 10,
        canUseShockTrap = true,
        shockDuration = 10,
        canUseFlashBomb = true,
        flashDuration = 10,
        canUseSonicBomb = true,
        canUseDungBomb = true,
        canUseMeat = true,
    )

    val monsterItemUsageList = listOf(
        monsterItemUsage.copy(monsterState = MonsterStateType.NORMAL),
        monsterItemUsage.copy(monsterState = MonsterStateType.ENRAGED),
    )

    // Monster Reward

    val monsterReward = MonsterReward(
        itemId = 1,
        itemName = "Item",
        itemIconType = ItemIconType.BALL,
        itemIconColor = ItemIconColor.YELLOW,
        condition = "Condition",
        rank = Rank.LOW,
        stackSize = 1,
        percentage = 10,
    )

    val monsterRewardList = listOf(
        monsterReward.copy(itemId = 1, itemName = "Item 1", condition = "Condition 1"),
        monsterReward.copy(itemId = 2, itemName = "Item 2", condition = "Condition 1"),
        monsterReward.copy(itemId = 3, itemName = "Item 3", condition = "Condition 1"),
        monsterReward.copy(itemId = 4, itemName = "Item 4", condition = "Condition 2"),
        monsterReward.copy(itemId = 5, itemName = "Item 5", condition = "Condition 2"),
        monsterReward.copy(itemId = 6, itemName = "Item 6", condition = "Condition 2"),
    )

}
