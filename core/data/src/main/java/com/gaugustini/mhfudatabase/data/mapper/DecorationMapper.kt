package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.DecorationWithText
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.model.Decoration

/**
 * Mapper for Decoration entities.
 */
object DecorationMapper {

    fun map(
        decoration: DecorationWithText,
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
            skills = emptyList(),
            recipeA = emptyList(),
            recipeB = emptyList(),
            equipmentType = null,
            quantity = null,
        )
    }

    fun mapList(
        decorations: List<DecorationWithText>,
    ): List<Decoration> {
        return decorations.map { map(it) }
    }

}
