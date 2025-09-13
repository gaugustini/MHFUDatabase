package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.data.enums.ItemIconColor
import com.gaugustini.mhfudatabase.data.enums.ItemIconType
import com.gaugustini.mhfudatabase.data.model.Decoration

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
        iconType = ItemIconType.JEWEL,
        iconColor = ItemIconColor.RED,
    )

    val decorationList = listOf(
        decoration.copy(id = 1, name = "Decoration 1", iconColor = ItemIconColor.RED),
        decoration.copy(id = 2, name = "Decoration 2", iconColor = ItemIconColor.BLUE),
        decoration.copy(id = 3, name = "Decoration 3", iconColor = ItemIconColor.GREEN),
    )

}
