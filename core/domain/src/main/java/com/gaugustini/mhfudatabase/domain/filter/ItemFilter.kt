package com.gaugustini.mhfudatabase.domain.filter

import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.ItemIconType
import com.gaugustini.mhfudatabase.domain.model.Item

/**
 * Filter for [Item].
 */
data class ItemFilter(
    val name: String? = null,
    val rarity: List<Int>? = null,
    //val category: ItemCategory, TODO: Add category when implemented
    val icons: List<ItemIconType>? = null,
    val iconColors: List<ItemIconColor>? = null,
)
