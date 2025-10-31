package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.data.enums.ArmorType
import com.gaugustini.mhfudatabase.data.enums.ItemIconColor
import com.gaugustini.mhfudatabase.data.enums.SkillCategory
import com.gaugustini.mhfudatabase.data.model.Skill
import com.gaugustini.mhfudatabase.data.model.SkillPointsArmor
import com.gaugustini.mhfudatabase.data.model.SkillPointsDecoration
import com.gaugustini.mhfudatabase.data.model.SkillTree
import com.gaugustini.mhfudatabase.data.model.SkillTreePoints

object PreviewSkillData {

    // Skill

    val skill = Skill(
        skillTreeId = 1,
        name = "Skill",
        description = "Skill Description",
        requiredPoints = 10,
    )

    val skillList = listOf(
        skill.copy(name = "Skill 1", requiredPoints = 10),
        skill.copy(name = "Skill 2", requiredPoints = 15),
        skill.copy(name = "Skill 3", requiredPoints = 20),
    )

    // Skill Tree

    val skillTree = SkillTree(
        id = 1,
        name = "Skill Tree",
        category = SkillCategory.COMBAT,
    )

    val skillTreeList = listOf(
        skillTree.copy(id = 1, name = "Skill Tree 1"),
        skillTree.copy(id = 2, name = "Skill Tree 2"),
        skillTree.copy(id = 3, name = "Skill Tree 3"),
    )

    // Skill Tree Points

    val skillTreePoints = SkillTreePoints(
        id = 1,
        name = "Skill",
        pointValue = 5
    )

    val skillTreePointsList = listOf(
        skillTreePoints.copy(id = 1, name = "Skill 1", pointValue = -5),
        skillTreePoints.copy(id = 2, name = "Skill 2", pointValue = 1),
        skillTreePoints.copy(id = 3, name = "Skill 3", pointValue = 5),
    )

    // Armor with SkillTree points

    val skillPointsArmor = SkillPointsArmor(
        armorId = 1,
        armorName = "Armor",
        armorType = ArmorType.HEAD,
        rarity = 5,
        skillTreeId = 1,
        pointValue = 5
    )

    val skillPointsArmorList = listOf(
        skillPointsArmor.copy(armorId = 1, armorName = "Armor 1", pointValue = 1),
        skillPointsArmor.copy(armorId = 2, armorName = "Armor 2", pointValue = 3),
        skillPointsArmor.copy(armorId = 3, armorName = "Armor 3", pointValue = 5),
    )

    // Decoration with SkillTree points

    val skillPointsDecoration = SkillPointsDecoration(
        decorationId = 1,
        decorationName = "Decoration",
        decorationColor = ItemIconColor.RED,
        skillTreeId = 1,
        pointValue = 5
    )

    val skillPointsDecorationList = listOf(
        skillPointsDecoration.copy(decorationId = 1, decorationName = "Decoration 1", pointValue = 1),
        skillPointsDecoration.copy(decorationId = 2, decorationName = "Decoration 2", pointValue = 3),
        skillPointsDecoration.copy(decorationId = 3, decorationName = "Decoration 3", pointValue = 5),
    )

}
