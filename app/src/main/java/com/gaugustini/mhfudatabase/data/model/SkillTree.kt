package com.gaugustini.mhfudatabase.data.model

import com.gaugustini.mhfudatabase.data.enums.ArmorType
import com.gaugustini.mhfudatabase.data.enums.ItemIconColor
import com.gaugustini.mhfudatabase.data.enums.SkillCategory

data class SkillTree(
    val id: Int,
    val name: String,
    val category: SkillCategory,
)

data class SkillTreeDetails(
    val skillTree: SkillTree,
    val skills: List<Skill>,
    val decorations: List<SkillPointsDecoration>,
    val armors: List<SkillPointsArmor>,
)

data class SkillTreePoints(
    val id: Int,
    val name: String,
    val pointValue: Int,
)

data class SkillPointsArmor(
    val armorId: Int,
    val armorName: String,
    val armorType: ArmorType,
    val rarity: Int,
    val skillTreeId: Int,
    val pointValue: Int,
)

data class SkillPointsDecoration(
    val decorationId: Int,
    val decorationName: String,
    val decorationColor: ItemIconColor,
    val skillTreeId: Int,
    val pointValue: Int,
)
