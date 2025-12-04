package com.gaugustini.mhfudatabase.data.model

import com.gaugustini.mhfudatabase.data.enums.ItemIconColor
import com.gaugustini.mhfudatabase.data.enums.ItemIconType
import com.gaugustini.mhfudatabase.data.enums.MonsterStateType
import com.gaugustini.mhfudatabase.data.enums.MonsterType
import com.gaugustini.mhfudatabase.data.enums.Rank
import com.gaugustini.mhfudatabase.data.enums.StatusType

data class Monster(
    val id: Int,
    val name: String,
    val ecology: String,
    val description: String,
    val type: MonsterType,
    val sizeSmallestMin: Int?,
    val sizeSmallestMax: Int?,
    val sizeLargestMin: Int?,
    val sizeLargestMax: Int?,
)

data class MonsterDetails(
    val monster: Monster,
    val damage: List<Hitzone>,
    val status: List<AilmentStatus>,
    val item: List<MonsterItemUsage>,
    val reward: List<MonsterReward>,
    val quest: List<Quest>,
)

data class Hitzone(
    val name: String,
    val cut: Int,
    val impact: Int,
    val shot: Int,
    val fire: Int,
    val water: Int,
    val thunder: Int,
    val ice: Int,
    val dragon: Int,
)

data class AilmentStatus(
    val type: StatusType,
    val initial: Int,
    val increase: Int,
    val max: Int,
    val duration: Int,
    val damage: Int,
)

data class MonsterItemUsage(
    val monsterState: MonsterStateType,
    val canUsePitfallTrap: Boolean,
    val canUseShockTrap: Boolean,
    val canUseFlashBomb: Boolean,
    val canUseSonicBomb: Boolean,
    val canUseDungBomb: Boolean,
    val canUseMeat: Boolean,
)

data class MonsterReward(
    val itemId: Int,
    val itemName: String,
    val monsterId: Int,
    val monsterName: String,
    val itemIconType: ItemIconType,
    val itemIconColor: ItemIconColor,
    val condition: String,
    val rank: Rank,
    val stackSize: Int,
    val percentage: Int?,
)
