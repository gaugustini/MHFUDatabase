package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.domain.enums.GatherType
import com.gaugustini.mhfudatabase.domain.enums.ItemCombinationType
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.ItemIconType
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.GatheringPoint
import com.gaugustini.mhfudatabase.domain.model.GatheringSource
import com.gaugustini.mhfudatabase.domain.model.Item
import com.gaugustini.mhfudatabase.domain.model.ItemCombination
import com.gaugustini.mhfudatabase.domain.model.ItemQuantity
import com.gaugustini.mhfudatabase.domain.model.ItemSources
import com.gaugustini.mhfudatabase.domain.model.ItemUsages
import com.gaugustini.mhfudatabase.domain.model.MonsterSource
import com.gaugustini.mhfudatabase.domain.model.Usage

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
        iconType = ItemIconType.ARMOR_STONE,
        iconColor = ItemIconColor.RED,
        sources = null,
        usages = null,
    )

    val itemList = listOf(
        item.copy(id = 1, name = "Item 1", iconType = ItemIconType.ARMOR_STONE),
        item.copy(id = 2, name = "Item 2", iconType = ItemIconType.FISH),
        item.copy(id = 3, name = "Item 3", iconType = ItemIconType.BONE),
    )

    // Item Combination

    val itemCombination = ItemCombination(
        itemCreated = item,
        itemA = item,
        itemB = item,
        type = ItemCombinationType.NORMAL,
        quantityMin = 1,
        quantityMax = 1,
        percentage = 100,
    )

    val itemCombinationList = listOf(
        itemCombination.copy(type = ItemCombinationType.NORMAL),
        itemCombination.copy(type = ItemCombinationType.TREASURE),
        itemCombination.copy(type = ItemCombinationType.ALCHEMY),
    )

    // Item Quantity

    val itemQuantity = ItemQuantity(
        item = item,
        quantity = 5,
    )

    val itemQuantityList = listOf(itemQuantity, itemQuantity, itemQuantity)

    // Item Location

    val itemLocation = GatheringPoint(
        rank = Rank.LOW,
        area = 1,
        type = GatherType.COLLECT,
        item = item
    )

    val itemLocationList = listOf(
        itemLocation.copy(type = GatherType.COLLECT),
        itemLocation.copy(type = GatherType.BUG),
        itemLocation.copy(type = GatherType.FISH),
        itemLocation.copy(type = GatherType.MINE),
    )

    /// Item Sources

    val gatheringSource = GatheringSource(
        location = PreviewLocationData.location,
        rank = Rank.LOW,
        type = GatherType.COLLECT,
        area = 1,
    )

    val gatheringSourceList = listOf(
        gatheringSource.copy(type = GatherType.COLLECT),
        gatheringSource.copy(type = GatherType.BUG),
        gatheringSource.copy(type = GatherType.FISH),
        gatheringSource.copy(type = GatherType.MINE),
    )

    val monsterSource = MonsterSource(
        monster = PreviewMonsterData.monster,
        condition = "Condition",
        rank = Rank.LOW,
        stackSize = 1,
        percentage = 100,
    )

    val monsterSourceList = listOf(
        monsterSource.copy(rank = Rank.LOW),
        monsterSource.copy(rank = Rank.HIGH),
        monsterSource.copy(rank = Rank.G),
        monsterSource.copy(rank = Rank.TREASURE),
        monsterSource.copy(rank = Rank.TRAINING),
    )

    val itemSources = ItemSources(
        combinations = itemCombinationList,
        locations = gatheringSourceList,
        monsterRewards = monsterSourceList,
    )

    // Item Usages

    val itemUsageArmor = Usage(
        craftable = PreviewArmorData.armor,
        quantity = 1,
    )

    val itemUsageArmorList = listOf(
        itemUsageArmor.copy(quantity = 1),
        itemUsageArmor.copy(quantity = 2),
        itemUsageArmor.copy(quantity = 3),
    )

    val itemUsageDecoration = Usage(
        craftable = PreviewDecorationData.decoration,
        quantity = 1,
    )

    val itemUsageDecorationList = listOf(
        itemUsageDecoration.copy(quantity = 1),
        itemUsageDecoration.copy(quantity = 2),
        itemUsageDecoration.copy(quantity = 3),
    )

    val itemUsageWeapon = Usage(
        craftable = PreviewWeaponData.weapon,
        quantity = 1,
    )

    val itemUsageWeaponList = listOf(
        itemUsageWeapon.copy(quantity = 1),
        itemUsageWeapon.copy(quantity = 2),
        itemUsageWeapon.copy(quantity = 3),
    )

    val itemUsages = ItemUsages(
        combinations = itemCombinationList,
        armors = itemUsageArmorList,
        decorations = itemUsageDecorationList,
        weapons = itemUsageWeaponList,
    )

}
