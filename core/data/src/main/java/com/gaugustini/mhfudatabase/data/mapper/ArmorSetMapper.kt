package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.ArmorSetWithText
import com.gaugustini.mhfudatabase.data.database.relation.ArmorWithText
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentSkillTreePoint
import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.ArmorSet

/**
 * Mapper for Armor Set entities.
 */
object ArmorSetMapper {

    fun toModel(
        armorSet: ArmorSetWithText,
        armors: List<ArmorWithText>? = null,
        skills: List<EquipmentSkillTreePoint>? = null,
        recipe: List<EquipmentItemQuantity>? = null,
    ): ArmorSet {
        return ArmorSet(
            id = armorSet.armorSet.id,
            name = armorSet.armorSetText.name,
            rank = Rank.fromString(armorSet.armorSet.rank),
            hunterType = HunterType.fromString(armorSet.armorSet.hunterType),
            rarity = armorSet.armorSet.rarity,
            defense = armorSet.defense,
            maxDefense = armorSet.maxDefense,
            fire = armorSet.fire,
            water = armorSet.water,
            thunder = armorSet.thunder,
            ice = armorSet.ice,
            dragon = armorSet.dragon,
            armors = armors?.map { ArmorMapper.toModel(it, emptyList(), emptyList()) },
            skills = skills?.map { SkillTreeMapper.toSkillPoint(it) },
            recipe = recipe?.map { ItemMapper.toItemQuantity(it) },
        )
    }

}
