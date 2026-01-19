package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.entity.item.ItemCombinationEntity
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.ItemWithText
import com.gaugustini.mhfudatabase.domain.enums.ItemCombinationType
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.ItemIconType
import com.gaugustini.mhfudatabase.domain.model.Item
import com.gaugustini.mhfudatabase.domain.model.ItemCombination
import com.gaugustini.mhfudatabase.domain.model.ItemQuantity
import com.gaugustini.mhfudatabase.domain.model.ItemSources
import com.gaugustini.mhfudatabase.domain.model.ItemUsages

/**
 * Mapper for Item entities.
 */
object ItemMapper {

    fun toModel(
        item: ItemWithText,
        sources: ItemSources,
        usages: ItemUsages,
    ): Item {
        return Item(
            id = item.item.id,
            name = item.itemText.name,
            description = item.itemText.description,
            rarity = item.item.rarity,
            buyPrice = item.item.buyPrice,
            sellPrice = item.item.sellPrice,
            carryMax = item.item.carryMax,
            iconType = ItemIconType.fromString(item.item.iconType),
            iconColor = ItemIconColor.fromString(item.item.iconColor),
            sources = sources,
            usages = usages,
        )
    }

    fun toItemQuantity(
        equipmentItemQuantity: EquipmentItemQuantity,
    ): ItemQuantity {
        return ItemQuantity(
            item = Item(
                id = equipmentItemQuantity.item.id,
                name = equipmentItemQuantity.itemText.name,
                description = equipmentItemQuantity.itemText.description,
                rarity = equipmentItemQuantity.item.rarity,
                buyPrice = equipmentItemQuantity.item.buyPrice,
                sellPrice = equipmentItemQuantity.item.sellPrice,
                carryMax = equipmentItemQuantity.item.carryMax,
                iconType = ItemIconType.fromString(equipmentItemQuantity.item.iconType),
                iconColor = ItemIconColor.fromString(equipmentItemQuantity.item.iconColor),
            ),
            quantity = equipmentItemQuantity.quantity,
        )
    }

    fun toItemCombination(
        combination: ItemCombinationEntity,
        itemCreated: Item,
        itemA: Item,
        itemB: Item,
    ): ItemCombination {
        return ItemCombination(
            itemCreated = itemCreated,
            itemA = itemA,
            itemB = itemB,
            type = ItemCombinationType.fromString(combination.combinationType),
            quantityMin = combination.quantityMin,
            quantityMax = combination.quantityMax,
            percentage = combination.percentage,
        )
    }

}
