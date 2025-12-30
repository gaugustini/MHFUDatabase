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

    fun map(
        item: ItemWithText,
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
            quantity = null,
            percentage = null,
        )
    }

    fun mapList(
        items: List<ItemWithText>,
    ): List<Item> {
        return items.map { map(it) }
    }

    fun mapList(
        items: List<ItemWithText>,
        combinations: List<ItemCombinationEntity>,
    ): List<ItemCombination> {
        val itemsById = items.associateBy { it.item.id }

        return combinations.map {
            ItemCombination(
                itemCreated = map(itemsById[it.itemCreatedId]!!),
                itemA = map(itemsById[it.itemAId]!!),
                itemB = map(itemsById[it.itemBId]!!),
                type = ItemCombinationType.fromString(it.combinationType),
                quantityMin = it.quantityMin,
                quantityMax = it.quantityMax,
                percentage = it.percentage,
            )
        }
    }

}
