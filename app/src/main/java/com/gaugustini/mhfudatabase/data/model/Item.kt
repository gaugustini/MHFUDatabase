package com.gaugustini.mhfudatabase.data.model

import com.gaugustini.mhfudatabase.data.enums.CombinationType
import com.gaugustini.mhfudatabase.data.enums.GatherType
import com.gaugustini.mhfudatabase.data.enums.ItemCategory
import com.gaugustini.mhfudatabase.data.enums.ItemIconColor
import com.gaugustini.mhfudatabase.data.enums.ItemIconType
import com.gaugustini.mhfudatabase.data.enums.Rank

data class Item(
    val id: Int,
    val name: String,
    val description: String,
    val rarity: Int,
    val buyPrice: Int,
    val sellPrice: Int,
    val carryMax: Int,
    val category: ItemCategory,
    val iconType: ItemIconType,
    val iconColor: ItemIconColor,
)

data class ItemDetails(
    val item: Item,
    val usages: List<String>,
    val sources: List<String>,
)

data class ItemCombination(
    val itemCreatedId: Int,
    val itemCreatedName: String,
    val itemCreatedIconType: ItemIconType,
    val itemCreatedIconColor: ItemIconColor,
    val itemAId: Int,
    val itemAName: String,
    val itemAIconType: ItemIconType,
    val itemAIconColor: ItemIconColor,
    val itemBId: Int,
    val itemBName: String,
    val itemBIconType: ItemIconType,
    val itemBIconColor: ItemIconColor,
    val type: CombinationType,
    val quantityMin: Int,
    val quantityMax: Int,
    val percentage: Int,
)

data class ItemQuantity(
    val id: Int,
    val name: String,
    val quantity: Int,
    val iconType: ItemIconType,
    val iconColor: ItemIconColor,
)

data class ItemLocation(
    val id: Int,
    val name: String,
    val rank: Rank,
    val type: GatherType,
    val area: Int,
    val iconType: ItemIconType,
    val iconColor: ItemIconColor,
)
