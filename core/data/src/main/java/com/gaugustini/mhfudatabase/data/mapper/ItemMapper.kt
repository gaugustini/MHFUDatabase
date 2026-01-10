package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.entity.item.ItemCombinationEntity
import com.gaugustini.mhfudatabase.data.database.relation.ItemWithText
import com.gaugustini.mhfudatabase.domain.enums.ItemCombinationType
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.ItemIconType
import com.gaugustini.mhfudatabase.domain.model.Item
import com.gaugustini.mhfudatabase.domain.model.ItemCombination

/**
 * Mapper for Item entities.
 */
object ItemMapper {

    fun toModel(
        item: ItemWithText,
        quantity: Int? = null,
        percentage: Int? = null,
    ): Item {
        return Item(
            id = item.item.id,
            name = item.itemText.name,
            description = item.itemText.description,
            rarity = item.item.rarity,
            buyPrice = item.item.buyPrice ?: 0,
            sellPrice = item.item.sellPrice,
            carryMax = item.item.carryMax,
            iconType = ItemIconType.fromString(item.item.iconType),
            iconColor = ItemIconColor.fromString(item.item.iconColor),
            quantity = quantity,
            percentage = percentage,
        )
    }

    fun toModel(
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
