package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.domain.enums.EquipmentType
import com.gaugustini.mhfudatabase.domain.model.EquipmentDecoration
import com.gaugustini.mhfudatabase.domain.model.UserEquipmentSet

object PreviewUserEquipmentSet {

    // User Equipment Set

    val userSet = UserEquipmentSet(
        id = 1,
        name = "Set",
        defense = 0,
        fire = 0,
        water = 0,
        thunder = 0,
        ice = 0,
        dragon = 0,
        weapon = null,
        armors = null,
        decorations = null,
        activeSkills = null,
        skills = null,
        recipe = null,
    )

    val userSetList = listOf(
        userSet.copy(id = 1, name = "Set 1"),
        userSet.copy(id = 2, name = "Set 2"),
        userSet.copy(id = 3, name = "Set 3"),
    )

    // User Equipment Decoration

    val decoration = EquipmentDecoration(
        equipmentType = EquipmentType.ARMOR_HEAD,
        decoration = PreviewDecorationData.decoration,
        quantity = 5,
    )

    val decorationList = listOf(
        decoration.copy(equipmentType = EquipmentType.ARMOR_HEAD),
        decoration.copy(equipmentType = EquipmentType.ARMOR_CHEST),
        decoration.copy(equipmentType = EquipmentType.ARMOR_LEGS),
    )

}
