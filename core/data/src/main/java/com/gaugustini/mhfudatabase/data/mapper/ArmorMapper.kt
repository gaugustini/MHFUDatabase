package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.ArmorWithText
import com.gaugustini.mhfudatabase.domain.enums.EquipmentType
import com.gaugustini.mhfudatabase.domain.enums.Gender
import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.domain.model.Armor

/**
 * Mapper for Armor entities.
 */
object ArmorMapper {

    fun map(
        armor: ArmorWithText,
    ): Armor {
        return Armor(
            id = armor.armor.id,
            armorSetId = armor.armor.armorSetId,
            name = armor.armorText.name,
            description = armor.armorText.description,
            type = EquipmentType.fromString(armor.armor.armorType),
            hunterType = HunterType.fromString(armor.armor.hunterType),
            gender = Gender.fromString(armor.armor.gender),
            rarity = armor.armor.rarity,
            price = armor.armor.price,
            numberOfSlots = armor.armor.numberOfSlots,
            defense = armor.armor.defense,
            maxDefense = armor.armor.maxDefense,
            fire = armor.armor.fireResistance,
            water = armor.armor.waterResistance,
            thunder = armor.armor.thunderResistance,
            ice = armor.armor.iceResistance,
            dragon = armor.armor.dragonResistance,
            skills = emptyList(),
            recipe = emptyList(),
        )
    }

    fun mapList(
        armors: List<ArmorWithText>,
    ): List<Armor> {
        return armors.map { map(it) }
    }

}
