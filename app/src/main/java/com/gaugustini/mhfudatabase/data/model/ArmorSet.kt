package com.gaugustini.mhfudatabase.data.model

import com.gaugustini.mhfudatabase.data.enums.HunterType
import com.gaugustini.mhfudatabase.data.enums.Rank

// TODO: Add Gender in Armor Set, defense and resistances
data class ArmorSet(
    val id: Int,
    val name: String,
    val rarity: Int,
    val rank: Rank,
    val hunterType: HunterType,
)

data class ArmorSetSummary(
    val armorSet: ArmorSet,
    val armors: List<Armor>,
)

data class ArmorSetDetails(
    val armorSet: ArmorSet,
    val defense: Int,
    val maxDefense: Int,
    val fire: Int,
    val water: Int,
    val thunder: Int,
    val ice: Int,
    val dragon: Int,
    val armors: List<Armor>,
    val skills: List<SkillTreePoints>,
    val recipe: List<ItemQuantity>,
)
