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
        skills: List<EquipmentSkillTreePoint>? = null,
        recipeA: List<EquipmentItemQuantity>? = null,
        recipeB: List<EquipmentItemQuantity>? = null,
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
            skills = skills?.map { SkillTreeMapper.toSkillPoint(it) },
            recipeA = recipeA?.map { ItemMapper.toItemQuantity(it) },
            recipeB = recipeB?.map { ItemMapper.toItemQuantity(it) },
        )
    }

}
