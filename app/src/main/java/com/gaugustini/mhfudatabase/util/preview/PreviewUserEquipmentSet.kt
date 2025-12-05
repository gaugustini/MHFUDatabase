package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.data.enums.EquipmentType
import com.gaugustini.mhfudatabase.data.enums.ItemIconColor
import com.gaugustini.mhfudatabase.data.model.EquipmentSetDecoration
import com.gaugustini.mhfudatabase.data.model.UserEquipmentSet

object PreviewUserEquipmentSet {

    // User Equipment Set

    val userSet = UserEquipmentSet(
        id = 1,
        name = "Set",
    )

    val userSetList = listOf(
        userSet.copy(id = 1, name = "Set 1"),
        userSet.copy(id = 2, name = "Set 2"),
        userSet.copy(id = 3, name = "Set 3"),
    )

    // User Equipment Decoration

    val decoration = EquipmentSetDecoration(
        setId = 1,
        decorationId = 1,
        name = "Decoration",
        requiredSlots = 1,
        decorationColor = ItemIconColor.RED,
        equipmentType = EquipmentType.WEAPON,
        quantity = 1,
    )

    val decorationList = listOf(
        decoration.copy(
            decorationId = 1,
            name = "Decoration 1",
            equipmentType = EquipmentType.ARMOR_HEAD
        ),
        decoration.copy(
            decorationId = 2,
            name = "Decoration 2",
            equipmentType = EquipmentType.ARMOR_CHEST
        ),
        decoration.copy(
            decorationId = 3,
            name = "Decoration 3",
            equipmentType = EquipmentType.ARMOR_LEGS
        ),
    )

}
