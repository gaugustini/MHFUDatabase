package com.gaugustini.mhfudatabase.data.model

import com.gaugustini.mhfudatabase.data.enums.ItemIconColor
import com.gaugustini.mhfudatabase.data.enums.ItemIconType

data class Decoration(
    val id: Int,
    val name: String,
    val description: String,
    val rarity: Int,
    val requiredSlots: Int,
    val buyPrice: Int,
    val sellPrice: Int,
    val iconType: ItemIconType,
    val iconColor: ItemIconColor,
)

data class DecorationDetails(
    val decoration: Decoration,
    val skills: List<SkillTreePoints>,
    val recipeA: List<ItemQuantity>,
    val recipeB: List<ItemQuantity>,
)
