package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.DecorationWithText
import com.gaugustini.mhfudatabase.domain.enums.EquipmentType
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.domain.model.Item
import com.gaugustini.mhfudatabase.domain.model.SkillTree

/**
 * Mapper for Decoration entities.
 */
object DecorationMapper {

    fun toModel(
        decoration: DecorationWithText,
        skills: List<SkillTree>,
        recipeA: List<Item>,
        recipeB: List<Item>,
        equipmentType: EquipmentType? = null,
        quantity: Int? = null,
    ): Decoration {
        return Decoration(
            id = decoration.decoration.id,
            name = decoration.decorationText.name,
            description = decoration.decorationText.description,
            rarity = decoration.item.rarity,
            buyPrice = decoration.item.buyPrice ?: 0,
            sellPrice = decoration.item.sellPrice,
            requiredSlots = decoration.decoration.requiredSlots,
            color = ItemIconColor.fromString(decoration.item.iconColor),
            skills = skills,
            recipeA = recipeA,
            recipeB = recipeB,
            equipmentType = equipmentType,
            quantity = quantity,
        )
    }

}
