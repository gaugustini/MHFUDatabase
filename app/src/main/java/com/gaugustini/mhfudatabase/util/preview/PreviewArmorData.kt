package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.data.enums.ArmorType
import com.gaugustini.mhfudatabase.data.enums.Gender
import com.gaugustini.mhfudatabase.data.enums.HunterType
import com.gaugustini.mhfudatabase.data.enums.Rank
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.ArmorSet

object PreviewArmorData {

    // Armor

    val armor = Armor(
        id = 1,
        armorSetId = 1,
        name = "Armor",
        description = "Armor Description",
        type = ArmorType.HEAD,
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
    )

    val armorList = listOf(
        armor.copy(id = 1, name = "Armor 1", type = ArmorType.HEAD),
        armor.copy(id = 2, name = "Armor 2", type = ArmorType.CHEST),
        armor.copy(id = 3, name = "Armor 3", type = ArmorType.ARMS),
        armor.copy(id = 4, name = "Armor 4", type = ArmorType.WAIST),
        armor.copy(id = 5, name = "Armor 5", type = ArmorType.LEGS),
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
    )

    val armorSetList = listOf(
        armorSet.copy(id = 1, name = "Armor Set 1", rarity = 1),
        armorSet.copy(id = 2, name = "Armor Set 2", rarity = 5),
        armorSet.copy(id = 3, name = "Armor Set 3", rarity = 10),
    )

}
