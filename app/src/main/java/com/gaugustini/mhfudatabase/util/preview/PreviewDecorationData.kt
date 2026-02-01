package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.model.Decoration

object PreviewDecorationData {

    // Decoration

    val decoration = Decoration(
        id = 1,
        name = "Decoration",
        description = "Decoration Description",
        rarity = 1,
        requiredSlots = 1,
        buyPrice = 100,
        sellPrice = 100,
        color = ItemIconColor.RED,
        skills = PreviewSkillData.skillPointList,
        recipeA = PreviewItemData.itemQuantityList,
        recipeB = PreviewItemData.itemQuantityList,
    )

    val decorationList = listOf(
        decoration.copy(id = 1, name = "Decoration 1", color = ItemIconColor.RED),
        decoration.copy(id = 2, name = "Decoration 2", color = ItemIconColor.BLUE),
        decoration.copy(id = 3, name = "Decoration 3", color = ItemIconColor.GREEN),
    )

}
