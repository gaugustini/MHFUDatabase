package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.DecorationWithText
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentSkillTreePoint
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.model.Decoration

/**
 * Mapper for Decoration entities.
 */
object DecorationMapper {

    fun toModel(
        decoration: DecorationWithText,
        skills: List<EquipmentSkillTreePoint>,
        recipeA: List<EquipmentItemQuantity>,
        recipeB: List<EquipmentItemQuantity>,
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
            skills = skills.map { SkillMapper.toSkillPoint(it) },
            recipeA = recipeA.map { ItemMapper.toItemQuantity(it) },
            recipeB = recipeB.map { ItemMapper.toItemQuantity(it) },
        )
    }

}
