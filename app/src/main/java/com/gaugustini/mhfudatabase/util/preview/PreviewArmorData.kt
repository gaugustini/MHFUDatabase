package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.domain.enums.EquipmentType
import com.gaugustini.mhfudatabase.domain.enums.Gender
import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.ArmorSet

object PreviewArmorData {

    // Armor

    val armor = Armor(
        id = 1,
        armorSetId = 1,
        name = "Armor",
        description = "Armor Description",
        type = EquipmentType.ARMOR_HEAD,
        hunterType = HunterType.BLADE,
        gender = Gender.MALE,
        rarity = 1,
        price = 100,
        numberOfSlots = 1,
        defense = 10,
        maxDefense = 10,
        fire = 10,
        water = 10,
        thunder = 10,
        ice = 10,
        dragon = 10,
        skills = null,
        recipe = null,
    )

    val armorList = listOf(
        armor.copy(id = 1, name = "Armor 1", type = EquipmentType.ARMOR_HEAD),
        armor.copy(id = 2, name = "Armor 2", type = EquipmentType.ARMOR_CHEST),
        armor.copy(id = 3, name = "Armor 3", type = EquipmentType.ARMOR_ARMS),
        armor.copy(id = 4, name = "Armor 4", type = EquipmentType.ARMOR_WAIST),
        armor.copy(id = 5, name = "Armor 5", type = EquipmentType.ARMOR_LEGS),
    )

    // Armor Set

    val armorSet = ArmorSet(
        id = 1,
        name = "Armor Set",
        rarity = 1,
        rank = Rank.LOW,
        hunterType = HunterType.BLADE,
        defense = 10,
        maxDefense = 10,
        fire = 10,
        water = 10,
        thunder = 10,
        ice = 10,
        dragon = 10,
        armors = null,
        skills = null,
        recipe = null,
    )

    val armorSetList = listOf(
        armorSet.copy(id = 1, name = "Armor Set 1", rarity = 1),
        armorSet.copy(id = 2, name = "Armor Set 2", rarity = 5),
        armorSet.copy(id = 3, name = "Armor Set 3", rarity = 10),
    )

}
