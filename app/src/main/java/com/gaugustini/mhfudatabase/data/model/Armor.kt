package com.gaugustini.mhfudatabase.data.model

import com.gaugustini.mhfudatabase.data.enums.ArmorType
import com.gaugustini.mhfudatabase.data.enums.Gender
import com.gaugustini.mhfudatabase.data.enums.HunterType
import com.gaugustini.mhfudatabase.data.enums.Rank

data class Armor(
    val id: Int,
    val name: String,
    val description: String,
    val type: ArmorType,
    val hunterType: HunterType,
    val gender: Gender,
    val rarity: Int,
    val price: Int,
    val numberOfSlots: Int,
    val defense: Int,
    val maxDefense: Int,
    val fire: Int,
    val water: Int,
    val thunder: Int,
    val ice: Int,
    val dragon: Int,
    val armorSetId: Int,
    val armorSetName: String,
    val armorSetRarity: Int,
    val armorSetRank: Rank,
    val armorSetHunterType: HunterType,
)

data class ArmorDetails(
    val armor: Armor,
    val skills: List<SkillTreePoints>,
    val recipe: List<ItemQuantity>,
)
