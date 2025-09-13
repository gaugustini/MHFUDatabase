package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.data.enums.CombinationType
import com.gaugustini.mhfudatabase.data.enums.GatherType
import com.gaugustini.mhfudatabase.data.enums.ItemCategory
import com.gaugustini.mhfudatabase.data.enums.ItemIconColor
import com.gaugustini.mhfudatabase.data.enums.ItemIconType
import com.gaugustini.mhfudatabase.data.enums.Rank
import com.gaugustini.mhfudatabase.data.model.Item
import com.gaugustini.mhfudatabase.data.model.ItemCombination
import com.gaugustini.mhfudatabase.data.model.ItemLocation
import com.gaugustini.mhfudatabase.data.model.ItemQuantity

object PreviewItemData {

    // Item

    val item = Item(
        id = 1,
        name = "Item",
        description = "Item Description",
        rarity = 1,
        buyPrice = 100,
        sellPrice = 100,
        carryMax = 1,
        category = ItemCategory.UNKNOWN,
        iconType = ItemIconType.ARMOR_STONE,
        iconColor = ItemIconColor.RED,
    )

    val itemList = listOf(
        item.copy(id = 1, name = "Item 1", iconType = ItemIconType.ARMOR_STONE),
        item.copy(id = 2, name = "Item 2", iconType = ItemIconType.FISH),
        item.copy(id = 3, name = "Item 3", iconType = ItemIconType.BONE),
    )

    // Item Combination

    val itemCombination = ItemCombination(
        itemCreatedId = 1,
        itemCreatedName = "Item Created",
        itemCreatedIconType = ItemIconType.ARMOR_STONE,
        itemCreatedIconColor = ItemIconColor.RED,
        itemAId = 2,
        itemAName = "Item A",
        itemAIconType = ItemIconType.ARMOR_STONE,
        itemAIconColor = ItemIconColor.RED,
        itemBId = 3,
        itemBName = "Item B",
        itemBIconType = ItemIconType.ARMOR_STONE,
        itemBIconColor = ItemIconColor.RED,
        type = CombinationType.NORMAL,
        quantityMin = 1,
        quantityMax = 1,
        percentage = 100,
    )

    val itemCombinationList = listOf(
        itemCombination.copy(
            itemCreatedId = 1,
            itemCreatedName = "Item Created 1",
            type = CombinationType.NORMAL,
        ),
        itemCombination.copy(
            itemCreatedId = 2,
            itemCreatedName = "Item Created 2",
            type = CombinationType.TREASURE,
        ),
        itemCombination.copy(
            itemCreatedId = 3,
            itemCreatedName = "Item Created 3",
            type = CombinationType.ALCHEMY,
        ),
    )

    // Item Quantity

    val itemQuantity = ItemQuantity(
        id = 1,
        name = "Item Quantity",
        quantity = 5,
        iconType = ItemIconType.ARMOR_STONE,
        iconColor = ItemIconColor.RED,
    )

    val itemQuantityList = listOf(
        itemQuantity.copy(id = 1, name = "Item Quantity 1", iconType = ItemIconType.ARMOR_STONE),
        itemQuantity.copy(id = 2, name = "Item Quantity 2", iconType = ItemIconType.FISH),
        itemQuantity.copy(id = 3, name = "Item Quantity 3", iconType = ItemIconType.BONE),
    )

    // Item Location

    val itemLocation = ItemLocation(
        id = 1,
        name = "Item Location",
        rank = Rank.LOW,
        type = GatherType.COLLECT,
        area = 1,
        iconType = ItemIconType.ARMOR_STONE,
        iconColor = ItemIconColor.RED,
    )

    val itemLocationList = listOf(
        itemLocation.copy(id = 1, name = "Item Location 1", type = GatherType.COLLECT),
        itemLocation.copy(id = 2, name = "Item Location 2", type = GatherType.BUG),
        itemLocation.copy(id = 3, name = "Item Location 3", type = GatherType.FISH),
        itemLocation.copy(id = 4, name = "Item Location 4", type = GatherType.MINE),
    )

}
