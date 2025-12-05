package com.gaugustini.mhfudatabase.data.model

import com.gaugustini.mhfudatabase.data.enums.EquipmentType
import com.gaugustini.mhfudatabase.data.enums.ItemIconColor

data class UserEquipmentSet(
    val id: Int,
    val name: String,
)

data class EquipmentSetDecoration(
    val setId: Int,
    val decorationId: Int,
    val name: String,
    val requiredSlots: Int,
    val decorationColor: ItemIconColor,
    val equipmentType: EquipmentType,
    val quantity: Int,
)

data class UserEquipmentSetDetails(
    val set: UserEquipmentSet,
    val weapon: Weapon?,
    val armors: List<Armor>,
    val decorations: List<EquipmentSetDecoration>,
)
