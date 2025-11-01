package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.data.enums.ArmorType
import com.gaugustini.mhfudatabase.data.enums.CombinationType
import com.gaugustini.mhfudatabase.data.enums.GatherType
import com.gaugustini.mhfudatabase.data.enums.ItemCategory
import com.gaugustini.mhfudatabase.data.enums.ItemIconColor
import com.gaugustini.mhfudatabase.data.enums.ItemIconType
import com.gaugustini.mhfudatabase.data.enums.Rank
import com.gaugustini.mhfudatabase.data.enums.WeaponType
import com.gaugustini.mhfudatabase.data.model.Item
import com.gaugustini.mhfudatabase.data.model.ItemCombination
import com.gaugustini.mhfudatabase.data.model.ItemLocation
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.ItemUsageArmor
import com.gaugustini.mhfudatabase.data.model.ItemUsageDecoration
import com.gaugustini.mhfudatabase.data.model.ItemUsageWeapon

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
        itemId = 1,
        itemName = "Item Location",
        locationId = 1,
        locationName = "Location",
        rank = Rank.LOW,
        type = GatherType.COLLECT,
        area = 1,
        iconType = ItemIconType.ARMOR_STONE,
        iconColor = ItemIconColor.RED,
    )

    val itemLocationList = listOf(
        itemLocation.copy(itemId = 1, itemName = "Item Location 1", type = GatherType.COLLECT),
        itemLocation.copy(itemId = 2, itemName = "Item Location 2", type = GatherType.BUG),
        itemLocation.copy(itemId = 3, itemName = "Item Location 3", type = GatherType.FISH),
        itemLocation.copy(itemId = 4, itemName = "Item Location 4", type = GatherType.MINE),
    )

    // Item Usages

    val itemUsageArmor = ItemUsageArmor(
        armorId = 1,
        armorName = "Armor",
        armorType = ArmorType.HEAD,
        rarity = 1,
        itemQuantity = 1,
    )

    val itemUsageArmorList = listOf(
        itemUsageArmor.copy(armorId = 1, armorName = "Armor 1", itemQuantity = 1),
        itemUsageArmor.copy(armorId = 2, armorName = "Armor 2", itemQuantity = 2),
        itemUsageArmor.copy(armorId = 3, armorName = "Armor 3", itemQuantity = 3),
        itemUsageArmor.copy(armorId = 4, armorName = "Armor 4", itemQuantity = 4),
    )

    val itemUsageDecoration = ItemUsageDecoration(
        decorationId = 1,
        decorationName = "Decoration",
        decorationColor = ItemIconColor.RED,
        itemQuantity = 1,
    )

    val itemUsageDecorationList = listOf(
        itemUsageDecoration.copy(
            decorationId = 1,
            decorationName = "Decoration 1",
            itemQuantity = 1
        ),
        itemUsageDecoration.copy(
            decorationId = 2,
            decorationName = "Decoration 2",
            itemQuantity = 2
        ),
        itemUsageDecoration.copy(
            decorationId = 3,
            decorationName = "Decoration 3",
            itemQuantity = 3
        ),
        itemUsageDecoration.copy(
            decorationId = 4,
            decorationName = "Decoration 4",
            itemQuantity = 4
        ),
    )

    val itemUsageWeapon = ItemUsageWeapon(
        weaponId = 1,
        weaponName = "Weapon",
        weaponType = WeaponType.GREAT_SWORD,
        rarity = 1,
        itemQuantity = 1,
    )

    val itemUsageWeaponList = listOf(
        itemUsageWeapon.copy(weaponId = 1, weaponName = "Weapon 1", itemQuantity = 1),
        itemUsageWeapon.copy(weaponId = 2, weaponName = "Weapon 2", itemQuantity = 2),
        itemUsageWeapon.copy(weaponId = 3, weaponName = "Weapon 3", itemQuantity = 3),
        itemUsageWeapon.copy(weaponId = 4, weaponName = "Weapon 4", itemQuantity = 4),
    )

}
