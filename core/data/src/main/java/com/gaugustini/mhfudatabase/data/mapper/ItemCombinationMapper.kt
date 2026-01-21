package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.entity.item.ItemCombinationEntity
import com.gaugustini.mhfudatabase.domain.enums.ItemCombinationType
import com.gaugustini.mhfudatabase.domain.model.Item
import com.gaugustini.mhfudatabase.domain.model.ItemCombination

/**
 * Mapper for Item Combination entity.
 */
object ItemCombinationMapper {

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
